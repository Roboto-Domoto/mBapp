#ifndef __SENSOR_H
#define __SENSOR_H
#include <cstdint>

class Sensor{

protected:  

    uint8_t pin;
    bool digital;

public: 

    Sensor(uint8_t pin,bool digital);
    int readValues();

};

#endif