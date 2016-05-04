package net.sf.openrocket.simulation;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.openrocket.aerodynamics.BarrowmanCalculator;
import net.sf.openrocket.camrocksim.AtmosphereData;
import net.sf.openrocket.formatting.MotorDescriptionSubstitutor;
import net.sf.openrocket.masscalc.BasicMassCalculator;
import net.sf.openrocket.models.atmosphere.AtmosphericModel;
import net.sf.openrocket.models.atmosphere.ExtendedISAModel;
import net.sf.openrocket.models.gravity.GravityModel;
import net.sf.openrocket.models.gravity.WGSGravityModel;
import net.sf.openrocket.models.wind.PinkNoiseWindModel;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.startup.Application;
import net.sf.openrocket.startup.Preferences;
import net.sf.openrocket.util.BugException;
import net.sf.openrocket.util.ChangeSource;
import net.sf.openrocket.util.GeodeticComputationStrategy;
import net.sf.openrocket.util.MathUtil;
import net.sf.openrocket.util.StateChangeListener;
import net.sf.openrocket.util.Utils;
import net.sf.openrocket.util.WorldCoordinate;

/**
 * A class holding simulation options in basic parameter form and which functions
 * as a ChangeSource.  A SimulationConditions instance is generated from this class
 * using {@link #toSimulationConditions()}.
 * 
 * @author Sampo Niskanen <sampo.niskanen@iki.fi>
 */
public class SimulationOptions implements ChangeSource, Cloneable {
	
	private static final Logger log = LoggerFactory.getLogger(SimulationOptions.class);
	
	public static final double MIN_LAUNCH_ROD_ANGLE = 0;
	public static final double AVG_LAUNCH_ROD_ANGLE = Math.PI / 4;
	public static final double MAX_LAUNCH_ROD_ANGLE = Math.PI / 2;
	
	/**
	 * The ISA standard atmosphere.
	 */
	private static final AtmosphericModel ISA_ATMOSPHERIC_MODEL = new ExtendedISAModel();
	
	protected final Preferences preferences = Application.getPreferences();
	
	private final Rocket rocket;
	private String motorID = null;
	
	// default ISA
	public AtmosphereData atmosphereData = new AtmosphereData(true);
	
	// [defaults] additional parameters for camrocksim
	private boolean monteCarloBool = preferences.getBoolean(Preferences.MONTE_CARLO_BOOL, false);
	private double monteCarloInt = preferences.getDouble(Preferences.MONTE_CARLO_INT, 10);
	
	private String atmosphereStr = preferences.getString(Preferences.ATMOSPHERE_STR, "../../Data/Atmospheres/wpNoWind.xml");
	
	private double sigmaLaunchDeclination = preferences.getDouble(Preferences.SIGMA_LAUNCH_DECLINATION, 0);
	private double sigmaThrust = preferences.getDouble(Preferences.SIGMA_THRUST, 0);
	
	/*
	 * NOTE:  When adding/modifying parameters, they must also be added to the
	 * equals and copyFrom methods!!
	 */
	
	private double launchRodLength = preferences.getDouble(Preferences.LAUNCH_ROD_LENGTH, 1);
	private boolean launchIntoWind = preferences.getBoolean(Preferences.LAUNCH_INTO_WIND, false);
	private double launchRodAngle = preferences.getDouble(Preferences.LAUNCH_ROD_ANGLE, 0);
	private double windDirection = preferences.getDouble(Preferences.WIND_DIRECTION, 0);
	private double launchRodDirection = preferences.getDouble(Preferences.LAUNCH_ROD_DIRECTION, 0);
	
	
	private double windAverage = preferences.getDouble(Preferences.WIND_AVERAGE, 0);
	private double windTurbulence = preferences.getDouble(Preferences.WIND_TURBULANCE, 0);
	
	/*
	 * SimulationOptions maintains the launch site parameters as separate double values,
	 * and converts them into a WorldCoordinate when converting to SimulationConditions.
	 */
	
	private double launchAltitude = preferences.getDouble(Preferences.LAUNCH_ALTITUDE, 0);
	private double launchLatitude = preferences.getDouble(Preferences.LAUNCH_LATITUDE, 28.61);
	private double launchLongitude = preferences.getDouble(Preferences.LAUNCH_LONGITUDE, -80.60);
	private GeodeticComputationStrategy geodeticComputation = GeodeticComputationStrategy.SPHERICAL;
	
	private boolean useISA = preferences.getBoolean(Preferences.LAUNCH_USE_ISA, true);
	private double launchTemperature = preferences.getDouble(Preferences.LAUNCH_TEMPERATURE, ExtendedISAModel.STANDARD_TEMPERATURE);
	private double launchPressure = preferences.getDouble(Preferences.LAUNCH_PRESSURE, ExtendedISAModel.STANDARD_PRESSURE);
	
	private double timeStep = preferences.getDouble(Preferences.SIMULATION_TIME_STEP, RK4SimulationStepper.RECOMMENDED_TIME_STEP);
	private double maximumAngle = RK4SimulationStepper.RECOMMENDED_ANGLE_STEP;
	
	private int randomSeed = new Random().nextInt();
	
	private boolean calculateExtras = true;
	
	
	private List<EventListener> listeners = new ArrayList<EventListener>();
	
	public SimulationOptions(Rocket rocket) {
		this.rocket = rocket;
	}
	
	public Rocket getRocket() {
		return rocket;
	}
	
	
	public String getMotorConfigurationID() {
		return motorID;
	}
	
	/**
	 * Set the motor configuration ID.  This must be a valid motor configuration ID of
	 * the rocket, otherwise the configuration is set to <code>null</code>.
	 * 
	 * @param id	the configuration to set.
	 */
	public void setMotorConfigurationID(String id) {
		if (id != null)
			id = id.intern();
		if (!rocket.isFlightConfigurationID(id))
			id = null;
		if (id == motorID)
			return;
		motorID = id;
		fireChangeEvent();
	}
	
	public String getAtmosphereString() {
		// returns the path for the wind profile
		return this.atmosphereStr;
	}
	
	public void setAtmosphereString(String newPath) {
		// sets the path for the wind profile
		this.atmosphereStr = newPath;
	}
	
	public double getMonteCarloInteger() {
		// returns numberof monte carlo runs
		return this.monteCarloInt;
	}
	
	public boolean getMonteCarloBool() {
		// returns whether to do monte carlo runs
		return this.monteCarloBool;
	}
	
	public void setMonteCarloInteger(double numberOfRuns) {
		// sets the number of monte carlo runs done by the simulation
		int numberInt = (int) numberOfRuns;
		this.monteCarloInt = (double) numberInt;
	}
	
	public void setMonteCarloBool(boolean doMonteCarlo) {
		// sets whether or not to do a monte carlo run, or simply do an average run
		this.monteCarloBool = doMonteCarlo;
	}
	
	public void setSigmaLaunchDeclination(double sigmaLaunchDeclination) {
		// sets declination angle stand deviation
		this.sigmaLaunchDeclination = sigmaLaunchDeclination;
	}
	
	public double getSigmaLaunchDeclination() {
		return this.sigmaLaunchDeclination;
	}
	
	public void setSigmaThrust(double sigmaThrust) {
		// set thrust
		this.sigmaThrust = sigmaThrust;
	}
	
	public double getSigmaThrust() {
		return this.sigmaThrust;
	}
	
	
	public double getLaunchRodLength() {
		return launchRodLength;
	}
	
	public void setLaunchRodLength(double launchRodLength) {
		if (MathUtil.equals(this.launchRodLength, launchRodLength))
			return;
		this.launchRodLength = launchRodLength;
		fireChangeEvent();
	}
	
	
	public boolean getLaunchIntoWind() {
		return launchIntoWind;
	}
	
	public void setLaunchIntoWind(boolean i) {
		launchIntoWind = i;
	}
	
	public double getLaunchRodAngle() {
		return launchRodAngle;
	}
	
	public void setLaunchRodAngle(double launchRodAngle) {
		launchRodAngle = MathUtil.clamp(launchRodAngle, -MAX_LAUNCH_ROD_ANGLE, MAX_LAUNCH_ROD_ANGLE);
		if (MathUtil.equals(this.launchRodAngle, launchRodAngle))
			return;
		this.launchRodAngle = launchRodAngle;
		fireChangeEvent();
	}
	
	
	public double getLaunchRodDirection() {
		if (launchIntoWind) {
			this.setLaunchRodDirection(windDirection);
		}
		return launchRodDirection;
	}
	
	public void setLaunchRodDirection(double launchRodDirection) {
		launchRodDirection = MathUtil.reduce360(launchRodDirection);
		if (MathUtil.equals(this.launchRodDirection, launchRodDirection))
			return;
		this.launchRodDirection = launchRodDirection;
		fireChangeEvent();
	}
	
	
	
	public double getWindSpeedAverage() {
		return windAverage;
	}
	
	public void setWindSpeedAverage(double windAverage) {
		if (MathUtil.equals(this.windAverage, windAverage))
			return;
		this.windAverage = MathUtil.max(windAverage, 0);
		fireChangeEvent();
	}
	
	
	public double getWindSpeedDeviation() {
		return windAverage * windTurbulence;
	}
	
	public void setWindSpeedDeviation(double windDeviation) {
		if (windAverage < 0.1) {
			windAverage = 0.1;
		}
		setWindTurbulenceIntensity(windDeviation / windAverage);
	}
	
	
	/**
	 * Return the wind turbulence intensity (standard deviation / average).
	 * 
	 * @return  the turbulence intensity
	 */
	public double getWindTurbulenceIntensity() {
		return windTurbulence;
	}
	
	/**
	 * Set the wind standard deviation to match the given turbulence intensity.
	 * 
	 * @param intensity   the turbulence intensity
	 */
	public void setWindTurbulenceIntensity(double intensity) {
		// Does not check equality so that setWindSpeedDeviation can be sure of event firing
		this.windTurbulence = intensity;
		fireChangeEvent();
	}
	
	
	/**
	 * Set the wind direction
	 * 
	 * @param direction the wind direction
	 */
	
	public void setWindDirection(double direction) {
		direction = MathUtil.reduce360(direction);
		if (launchIntoWind) {
			this.setLaunchRodDirection(direction);
		}
		if (MathUtil.equals(this.windDirection, direction))
			return;
		this.windDirection = direction;
		fireChangeEvent();
		
	}
	
	public double getWindDirection() {
		return this.windDirection;
	}
	
	public double getLaunchAltitude() {
		return launchAltitude;
	}
	
	public void setLaunchAltitude(double altitude) {
		if (MathUtil.equals(this.launchAltitude, altitude))
			return;
		this.launchAltitude = altitude;
		fireChangeEvent();
	}
	
	
	public double getLaunchLatitude() {
		return launchLatitude;
	}
	
	public void setLaunchLatitude(double launchLatitude) {
		launchLatitude = MathUtil.clamp(launchLatitude, -90, 90);
		if (MathUtil.equals(this.launchLatitude, launchLatitude))
			return;
		this.launchLatitude = launchLatitude;
		fireChangeEvent();
	}
	
	public double getLaunchLongitude() {
		return launchLongitude;
	}
	
	public void setLaunchLongitude(double launchLongitude) {
		launchLongitude = MathUtil.clamp(launchLongitude, -180, 180);
		if (MathUtil.equals(this.launchLongitude, launchLongitude))
			return;
		this.launchLongitude = launchLongitude;
		fireChangeEvent();
	}
	
	
	public GeodeticComputationStrategy getGeodeticComputation() {
		return geodeticComputation;
	}
	
	public void setGeodeticComputation(GeodeticComputationStrategy geodeticComputation) {
		if (this.geodeticComputation == geodeticComputation)
			return;
		if (geodeticComputation == null) {
			throw new IllegalArgumentException("strategy cannot be null");
		}
		this.geodeticComputation = geodeticComputation;
		fireChangeEvent();
	}
	
	
	public boolean isISAAtmosphere() {
		return useISA;
	}
	
	public void setISAAtmosphere(boolean isa) {
		if (isa == useISA)
			return;
		useISA = isa;
		fireChangeEvent();
	}
	
	
	public double getLaunchTemperature() {
		return launchTemperature;
	}
	
	
	
	public void setLaunchTemperature(double launchTemperature) {
		if (MathUtil.equals(this.launchTemperature, launchTemperature))
			return;
		this.launchTemperature = launchTemperature;
		fireChangeEvent();
	}
	
	
	
	public double getLaunchPressure() {
		return launchPressure;
	}
	
	
	
	public void setLaunchPressure(double launchPressure) {
		if (MathUtil.equals(this.launchPressure, launchPressure))
			return;
		this.launchPressure = launchPressure;
		fireChangeEvent();
	}
	
	
	/**
	 * Returns an atmospheric model corresponding to the launch conditions.  The
	 * atmospheric models may be shared between different calls.
	 * 
	 * @return	an AtmosphericModel object.
	 */
	private AtmosphericModel getAtmosphericModel() {
		if (useISA) {
			return ISA_ATMOSPHERIC_MODEL;
		}
		return new ExtendedISAModel(getLaunchAltitude(), launchTemperature, launchPressure);
	}
	
	
	public double getTimeStep() {
		return timeStep;
	}
	
	public void setTimeStep(double timeStep) {
		if (MathUtil.equals(this.timeStep, timeStep))
			return;
		this.timeStep = timeStep;
		fireChangeEvent();
	}
	
	public double getMaximumStepAngle() {
		return maximumAngle;
	}
	
	public void setMaximumStepAngle(double maximumAngle) {
		maximumAngle = MathUtil.clamp(maximumAngle, 1 * Math.PI / 180, 20 * Math.PI / 180);
		if (MathUtil.equals(this.maximumAngle, maximumAngle))
			return;
		this.maximumAngle = maximumAngle;
		fireChangeEvent();
	}
	
	
	
	public boolean getCalculateExtras() {
		return calculateExtras;
	}
	
	
	
	public void setCalculateExtras(boolean calculateExtras) {
		if (this.calculateExtras == calculateExtras)
			return;
		this.calculateExtras = calculateExtras;
		fireChangeEvent();
	}
	
	
	
	public int getRandomSeed() {
		return randomSeed;
	}
	
	public void setRandomSeed(int randomSeed) {
		if (this.randomSeed == randomSeed) {
			return;
		}
		this.randomSeed = randomSeed;
		/*
		 * This does not fire an event since we don't want to invalidate simulation results
		 * due to changing the seed value.  This needs to be revisited if the user is ever
		 * allowed to select the seed value.
		 */
		//		fireChangeEvent();
	}
	
	/**
	 * Randomize the random seed value.
	 */
	public void randomizeSeed() {
		this.randomSeed = new Random().nextInt();
		//		fireChangeEvent();
	}
	
	
	
	@Override
	public SimulationOptions clone() {
		try {
			SimulationOptions copy = (SimulationOptions) super.clone();
			copy.listeners = new ArrayList<EventListener>();
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new BugException(e);
		}
	}
	
	
	public void copyFrom(SimulationOptions src) {
		
		if (this.rocket == src.rocket) {
			
			this.motorID = src.motorID;
			
		} else {
			
			if (src.rocket.hasMotors(src.motorID)) {
				// First check for exact match:
				if (this.rocket.isFlightConfigurationID(src.motorID)) {
					this.motorID = src.motorID;
				} else {
					// Try to find a closely matching motor ID
					MotorDescriptionSubstitutor formatter = Application.getInjector().getInstance(MotorDescriptionSubstitutor.class);
					
					String motorDesc = formatter.getMotorConfigurationDescription(src.rocket, src.motorID);
					String matchID = null;
					
					for (String id : this.rocket.getFlightConfigurationIDs()) {
						String motorDesc2 = formatter.getMotorConfigurationDescription(this.rocket, id);
						if (motorDesc.equals(motorDesc2)) {
							matchID = id;
							break;
						}
					}
					
					this.motorID = matchID;
				}
			} else {
				this.motorID = null;
			}
		}
		
		this.launchAltitude = src.launchAltitude;
		this.launchLatitude = src.launchLatitude;
		this.launchLongitude = src.launchLongitude;
		this.launchPressure = src.launchPressure;
		this.launchRodAngle = src.launchRodAngle;
		this.launchRodDirection = src.launchRodDirection;
		this.launchRodLength = src.launchRodLength;
		this.launchTemperature = src.launchTemperature;
		this.maximumAngle = src.maximumAngle;
		this.timeStep = src.timeStep;
		this.windAverage = src.windAverage;
		this.windTurbulence = src.windTurbulence;
		this.windDirection = src.windDirection;
		this.calculateExtras = src.calculateExtras;
		this.randomSeed = src.randomSeed;
		
		// camrocksim attributes
		this.monteCarloBool = src.monteCarloBool;
		this.monteCarloInt = src.monteCarloInt;
		this.atmosphereStr = src.atmosphereStr;
		this.sigmaLaunchDeclination = src.sigmaLaunchDeclination;
		this.sigmaThrust = src.sigmaThrust;
		
		fireChangeEvent();
	}
	
	public void copyConditionsFrom(SimulationOptions src) {
		// Be a little smart about triggering the change event.
		// only do it if one of the "important" (user specified) parameters has really changed.
		boolean isChanged = true;
		if (this.launchAltitude != src.launchAltitude) {
			isChanged = true;
			this.launchAltitude = src.launchAltitude;
		}
		if (this.launchLatitude != src.launchLatitude) {
			isChanged = true;
			this.launchLatitude = src.launchLatitude;
		}
		if (this.launchLongitude != src.launchLongitude) {
			isChanged = true;
			this.launchLongitude = src.launchLongitude;
		}
		if (this.launchPressure != src.launchPressure) {
			isChanged = true;
			this.launchPressure = src.launchPressure;
		}
		if (this.launchRodAngle != src.launchRodAngle) {
			isChanged = true;
			this.launchRodAngle = src.launchRodAngle;
		}
		if (this.launchRodDirection != src.launchRodDirection) {
			isChanged = true;
			this.launchRodDirection = src.launchRodDirection;
		}
		if (this.launchRodLength != src.launchRodLength) {
			isChanged = true;
			this.launchRodLength = src.launchRodLength;
		}
		if (this.launchTemperature != src.launchTemperature) {
			isChanged = true;
			this.launchTemperature = src.launchTemperature;
		}
		if (this.maximumAngle != src.maximumAngle) {
			isChanged = true;
			this.maximumAngle = src.maximumAngle;
		}
		this.maximumAngle = src.maximumAngle;
		if (this.timeStep != src.timeStep) {
			isChanged = true;
			this.timeStep = src.timeStep;
		}
		if (this.windAverage != src.windAverage) {
			isChanged = true;
			this.windAverage = src.windAverage;
		}
		if (this.windDirection != src.windDirection) {
			isChanged = true;
			this.windDirection = src.windDirection;
		}
		if (this.windTurbulence != src.windTurbulence) {
			isChanged = true;
			this.windTurbulence = src.windTurbulence;
		}
		if (this.calculateExtras != src.calculateExtras) {
			isChanged = true;
			this.calculateExtras = src.calculateExtras;
		}
		
		// added camrocksim attributes
		this.monteCarloBool = src.monteCarloBool;
		this.monteCarloInt = src.monteCarloInt;
		this.atmosphereStr = src.atmosphereStr;
		this.sigmaLaunchDeclination = src.sigmaLaunchDeclination;
		this.sigmaThrust = src.sigmaThrust;
		
		
		if (isChanged) {
			// Only copy the randomSeed if something else has changed.
			// Honestly, I don't really see a need for that.
			this.randomSeed = src.randomSeed;
			fireChangeEvent();
		}
	}
	
	
	/**
	 * Compares whether the two simulation conditions are equal.  The two are considered
	 * equal if the rocket, motor id and all variables are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SimulationOptions))
			return false;
		SimulationOptions o = (SimulationOptions) other;
		return ((this.rocket == o.rocket) &&
				Utils.equals(this.motorID, o.motorID) &&
				MathUtil.equals(this.launchAltitude, o.launchAltitude) &&
				MathUtil.equals(this.launchLatitude, o.launchLatitude) &&
				MathUtil.equals(this.launchLongitude, o.launchLongitude) &&
				MathUtil.equals(this.launchPressure, o.launchPressure) &&
				MathUtil.equals(this.launchRodAngle, o.launchRodAngle) &&
				MathUtil.equals(this.launchRodDirection, o.launchRodDirection) &&
				MathUtil.equals(this.launchRodLength, o.launchRodLength) &&
				MathUtil.equals(this.launchTemperature, o.launchTemperature) &&
				MathUtil.equals(this.maximumAngle, o.maximumAngle) &&
				MathUtil.equals(this.timeStep, o.timeStep) &&
				MathUtil.equals(this.windAverage, o.windAverage) &&
				MathUtil.equals(this.windTurbulence, o.windTurbulence) &&
				MathUtil.equals(this.windDirection, o.windDirection) &&
				this.calculateExtras == o.calculateExtras && this.randomSeed == o.randomSeed);
	}
	
	/**
	 * Hashcode method compatible with {@link #equals(Object)}.
	 */
	@Override
	public int hashCode() {
		if (motorID == null)
			return rocket.hashCode();
		return rocket.hashCode() + motorID.hashCode();
	}
	
	@Override
	public void addChangeListener(StateChangeListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeChangeListener(StateChangeListener listener) {
		listeners.remove(listener);
	}
	
	private final EventObject event = new EventObject(this);
	
	private void fireChangeEvent() {
		
		// Copy the list before iterating to prevent concurrent modification exceptions.
		EventListener[] list = listeners.toArray(new EventListener[0]);
		for (EventListener l : list) {
			if (l instanceof StateChangeListener) {
				((StateChangeListener) l).stateChanged(event);
			}
		}
	}
	
	
	// TODO: HIGH: Clean up
	public SimulationConditions toSimulationConditions() {
		SimulationConditions conditions = new SimulationConditions();
		
		conditions.setRocket((Rocket) getRocket().copy());
		conditions.setMotorConfigurationID(getMotorConfigurationID());
		conditions.setLaunchRodLength(getLaunchRodLength());
		conditions.setLaunchRodAngle(getLaunchRodAngle());
		conditions.setLaunchRodDirection(getLaunchRodDirection());
		conditions.setLaunchSite(new WorldCoordinate(getLaunchLatitude(), getLaunchLongitude(), getLaunchAltitude()));
		conditions.setGeodeticComputation(getGeodeticComputation());
		conditions.setRandomSeed(randomSeed);
		
		PinkNoiseWindModel windModel = new PinkNoiseWindModel(randomSeed);
		windModel.setAverage(getWindSpeedAverage());
		windModel.setStandardDeviation(getWindSpeedDeviation());
		windModel.setDirection(windDirection);
		
		conditions.setWindModel(windModel);
		
		conditions.setAtmosphericModel(getAtmosphericModel());
		
		GravityModel gravityModel = new WGSGravityModel();
		
		conditions.setGravityModel(gravityModel);
		
		conditions.setAerodynamicCalculator(new BarrowmanCalculator());
		conditions.setMassCalculator(new BasicMassCalculator());
		
		conditions.setTimeStep(getTimeStep());
		conditions.setMaximumAngleStep(getMaximumStepAngle());
		
		conditions.setCalculateExtras(getCalculateExtras());
		
		return conditions;
	}
	
}