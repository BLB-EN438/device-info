import { WebPlugin } from '@capacitor/core';

import type { DeviceInfoPlugin } from './definitions';

export class DeviceInfoWeb extends WebPlugin implements DeviceInfoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
