#ifndef __CONTEXT_H
#define __CONTEXT_H
#include <DHTesp.h>

class Context{

    float* temperatures;
    float* humidities;
    bool doorOpen;
    int length;

public:

    Context();
    Context(const uint8_t DHT);

    void setTemperature(const float temperature,const uint8_t nSensor);
    void setHumidity(const float humidity,const uint8_t nSensor);
    void setDoorOpen(const bool doorOpen);
    void setData(const TempAndHumidity data,const uint8_t nSensor);

    String c_str();

    ~Context();
};

#endif