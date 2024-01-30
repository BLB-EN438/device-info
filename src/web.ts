import { WebPlugin } from '@capacitor/core';

import type { DeviceInfoPlugin } from './definitions';

import { DeviceInfo } from './definitions';

export class DeviceInfoWeb extends WebPlugin implements DeviceInfoPlugin {
  async getId(): Promise<{ value: string; }> {
    return {value:"demo123456"};
  }
  async get(): Promise<{ results: DeviceInfo; }> {
    console.log('deviceInfo: ', 'This is web config');
    return {
      results: {
        "serial": "demo-12345",
        "ssid": "BLB-wifi",
        "batteryLevel": 100,
        "platform": "Web",
        "operatingSystem": "",
        "model": "",
        "manufacturer": "",
        "osVersion": "",
        "networkConnected": false,
        "networkType": "",
        "batteryCharging":false
      }
    };
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
