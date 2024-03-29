#ifndef __INFO_H
#define __INFO_H
#include <DHTesp.h>

class Context{

    double* temperatures;
    double* humidities;
    bool doorOpen;

public:

    Context(const uint8_t DHT);

    void setTemperature(const double temperature,const uint8_t nSensor);
    void setHumidity(const double humidity,const uint8_t nSensor);
    void setDoorOpen(const bool doorOpen);
    void setData(const TempAndHumidity data,const uint8_t nSensor);

};

#endif