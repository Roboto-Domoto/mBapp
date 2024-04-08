#ifndef __SENSORS_H
#define __SENSORS_H
#include <DHTesp.h>
#include <sensors/Bumper.h>
#include <sensors/Pressures.h>
#include <structures/Context.h>

class Sensors{

    DHTesp topDHT;
    DHTesp doorDHT;
    DHTesp bottomDHT;
    Bumper doorBumper;
    Pressure topPressure;
    Pressure bottonPressure;
    Context actual;
    
public:

    

    Sensors(
        uint8_t pinTopDHT,uint8_t pinDoorDHT,uint8_t pinBottomDHT,
        uint8_t pinDoorBumper,uint8_t pinTopPressure,uint8_t pinBottomPressure);

    String generateContext();

    int getPressure(bool top);

};

#endif