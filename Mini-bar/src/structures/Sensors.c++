#include <structures/Sensors.h>
#include <sensors/Bumper.h>
#include <iostream>
#include <sstream>


#define DHT_TYPE DHTesp::DHT11

Sensors::Sensors(
        uint8_t pinTopDHT,uint8_t pinDoorDHT,uint8_t pinBottomDHT,
        uint8_t pinDoorBumper,uint8_t pinTopPressure,uint8_t pinBottomPressure){
    this->topDHT.setup(pinTopDHT,DHT_TYPE);
    this->doorDHT.setup(pinDoorDHT,DHT_TYPE);
    this->bottomDHT.setup(pinBottomDHT,DHT_TYPE);
    this->doorBumper = Bumper(pinDoorBumper);
    this->topPressure = Pressure(pinTopPressure);
    this->bottonPressure = Pressure(pinBottomPressure);      
}

String Sensors::generateContext(){
    std::ostringstream formatted;
    formatted << "T:" << topDHT.getTempAndHumidity().temperature << "," << doorDHT.getTempAndHumidity().temperature << "," << bottomDHT.getTempAndHumidity().temperature;
    formatted << "|D:" << this->doorBumper.isClosed();
    formatted << "|P:" << this->topPressure.getPressure() << "," << this->bottonPressure.getPressure() << "\n";
    std::string s = formatted.str();
    return s.c_str();
}

int Sensors::getPressure(bool top){
    return top? this->topPressure.getPressure() : this->bottonPressure.getPressure();
}

