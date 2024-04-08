#include <structures/Sensors.h>
#include <structures/Context.h>
#include <sensors/Bumper.h>

#define DHT_TYPE DHTesp::DHT11

Sensors::Sensors(
        uint8_t pinTopDHT,uint8_t pinDoorDHT,uint8_t pinBottomDHT,
        uint8_t pinDoorBumper,uint8_t pinTopPressure,uint8_t pinBottomPressure){
    this->topDHT.setup(pinTopDHT,DHT_TYPE);
    this->doorDHT.setup(pinDoorDHT,DHT_TYPE);
    this->bottomDHT.setup(pinBottomDHT,DHT_TYPE);
    this->doorBumper = Bumper(pinDoorBumper);
    this->topPressure = Pressure(pinTopPressure);
    this->bottonPressure = Pressure(pinBottomPressure);     
    this->actual = Context(3); 
}

String Sensors::generateContext(){
    actual.setDoorOpen(this->doorBumper.isClosed());
    actual.setData(topDHT.getTempAndHumidity(),0);
    actual.setData(doorDHT.getTempAndHumidity(),1);
    actual.setData(bottomDHT.getTempAndHumidity(),2);
    String s = actual.c_str();
    return s;
}

int Sensors::getPressure(bool top){
    return top? this->topPressure.getPressure() : this->bottonPressure.getPressure();
}

