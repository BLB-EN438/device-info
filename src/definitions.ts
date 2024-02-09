export interface DeviceInfoPlugin {
  serialId(): Promise<{ value: string }>;
  batteryLevel(): Promise<{ value: number}>;
  isBatteryCharging(): Promise<{ value: boolean}>;
  manufacturer(): Promise<{ value: string }>;
  model(): Promise<{ value: string }>;
  operatingSystem(): Promise<{ value: string}>;
  osVersion(): Promise<{ value: string}>;
  platform(): Promise<{ value: string}>;
  isNetworkConnected(): Promise<{ value: boolean}>;
  networkType(): Promise<{ value: string}>;
  wifiSSID(): Promise<{ value: string}>;
}
