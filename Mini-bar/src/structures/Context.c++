#include <structures/Context.h>

Context::Context(const uint8_t nDHT){
    this->doorOpen = false;
    this->humidities = new double[nDHT];
    this->temperatures = new double[nDHT];
}

void Context::setTemperature(const double temperature,const uint8_t nSensor){
    this->temperatures[nSensor] = temperature;
}

void Context::setHumidity(const double humidity,const uint8_t nSensor){
    this->humidities[nSensor] = humidity;
}

void Context::setDoorOpen(const bool doorOpen){
    this->doorOpen = doorOpen;
}

void Context::setData(const TempAndHumidity data,const uint8_t nSensor){
    this->setTemperature(data.temperature,nSensor);
    this->setHumidity(data.humidity,nSensor);
}