export interface DeviceInfoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
