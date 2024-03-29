#include "../../include/sensors/Sensor.h"
#include "Arduino.h"

Sensor::Sensor(uint8_t pin,bool digital){
    this->pin=pin;
    this->digital=digital;
    pinMode(this->pin,INPUT);
}

//Valores entre 0 y 4095
uint8_t Sensor::readValues(){
    if(this->digital) return static_cast<uint16_t>(digitalRead(this->pin));
    else return analogRead(this->pin);
}