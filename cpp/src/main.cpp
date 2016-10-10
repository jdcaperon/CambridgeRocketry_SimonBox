/*
%## Copyright (C) 2008 S.Box
%## testproject2.cpp

%## Author: S.Box
%## Created: 2008-05-27
*/

#include<iostream>
//#include"vmaths.h"
//#include<cmath>
//#include"intabread.h"
#include"ascentcalc.h"
#include"descentcalc.h"
#include"RocketFlight.h"
#include"MonteFy.h"
#include"HandleInputFile.h"


using namespace std;


int main(int argc, char* argv[]){

    string filename;
	if (argc ==1){
		filename = "./SimulationInput.xml";
	}
	else if (argc ==2){
		filename = argv[1];
	}
	else{
		cout<<"Unrecognised command line options"<<endl;
	}

	// cout<<filename<<endl;
	HandleInputFile HI(filename);
	return(0);
}