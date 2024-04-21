#include "../../include/sensors/Sensor.h"
#include "Arduino.h"

Sensor::Sensor(uint8_t pin,bool digital){
    this->pin=pin;
    this->digital=digital;
    pinMode(this->pin,INPUT_PULLDOWN);
}

//Valores entre 0 y 4095
int Sensor::readValues(){
    if(this->digital) return digitalRead(this->pin);
    else return analogRead(this->pin);
}