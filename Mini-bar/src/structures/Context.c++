#include <structures/Context.h>
#include <iostream>
#include <sstream>


Context::Context(){
    this->doorOpen = false;
    this->humidities = NULL;
    this->temperatures = NULL;
}
Context::Context(const uint8_t nDHT){
    this->doorOpen = false;
    this->humidities = new float[nDHT];
    this->temperatures = new float[nDHT];
}

void Context::setTemperature(const float temperature,const uint8_t nSensor){
    this->temperatures[nSensor] = temperature;
}

void Context::setHumidity(const float humidity,const uint8_t nSensor){
    this->humidities[nSensor] = humidity;
}

void Context::setDoorOpen(const bool doorOpen){
    this->doorOpen = doorOpen;
}

void Context::setData(const TempAndHumidity data,const uint8_t nSensor){
    this->setTemperature(data.temperature,nSensor);
    this->setHumidity(data.humidity,nSensor);
}

String Context::c_str(){
    std::ostringstream formatted;
    formatted << "T:" << temperatures[0] << "," << temperatures[1] << "," << temperatures[2];
    formatted << "|H:" << humidities[0] << "," << humidities[1] << "," << humidities[2];
    formatted << "|D:" << this->doorOpen << "\n";
    std::string s = formatted.str();
    return s.c_str();
}


Context::~Context(){
    free(this->humidities);
    free(this->temperatures);
}