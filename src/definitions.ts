export interface DeviceInfo {
  serial?: string;
  batteryLevel?: number;
  batteryCharging?: boolean;

  platform?: string;
  operatingSystem?: string;
  model?: string;
  manufacturer?: string;
  osVersion?: string;

  networkConnected?: boolean;
  ssid?: string;
  networkType?: string;
}
export interface DeviceInfoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  get(): Promise<{ results: DeviceInfo }>;
  getId(): Promise<{ value: string }>;
}
