#ifndef __PRESSURE_H
#define __PRESSURE_H
#include "Sensor.h"

class Pressure: Sensor{

public:

    Pressure(): Sensor(0,false){}
    Pressure(uint8_t pin):Sensor(pin,false){}

};

#endif