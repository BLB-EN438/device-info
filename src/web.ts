import { WebPlugin } from '@capacitor/core';

import type { DeviceInfoPlugin } from './definitions';


export class DeviceInfoWeb extends WebPlugin implements DeviceInfoPlugin {
  async serialId(): Promise<{ value: string; }> {
    return {value:"demo123456"};
  }
  async batteryLevel(): Promise<{ value: number; }> {
    return {value:50};
  }
  async isBatteryCharging(): Promise<{ value: boolean; }> {
    return {value:false};
  }
  async  manufacturer(): Promise<{ value: string; }> {
    return {value:"Manufacturer"};
  }
  async model(): Promise<{ value: string; }> {
    return {value:"Model1"};
  }
  async operatingSystem(): Promise<{ value: string; }> {
    return {value:"MACOS"};
  }
  async osVersion(): Promise<{ value: string; }> {
    return {value:"007"};
  }
  async platform(): Promise<{ value: string; }> {
    return {value:"Web"};
  }
  async isNetworkConnected(): Promise<{ value: boolean; }> {
    return {value:false};
  }
  async networkType(): Promise<{ value: string; }> {
    return {value:"NetworkType"};
  }
  async wifiSSID(): Promise<{ value: string; }> {
    return {value:"WebWiFi"};
  }
}
