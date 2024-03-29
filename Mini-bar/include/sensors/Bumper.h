#ifndef __BUMPERS_H
#define __BUMPERS_H
#include "Sensor.h"

class Bumper: Sensor{

public: 

    Bumper(): Sensor(0,true){}
    Bumper(uint8_t pin): Sensor(pin,true){}

    bool isClosed();

};

#endif