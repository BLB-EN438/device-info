# device-info

Get device info

## Install

```bash
npm install device-info
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`get()`](#get)
* [`getId()`](#getid)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### get()

```typescript
get() => Promise<{ results: DeviceInfo; }>
```

**Returns:** <code>Promise&lt;{ results: <a href="#deviceinfo">DeviceInfo</a>; }&gt;</code>

--------------------


### getId()

```typescript
getId() => Promise<{ value: string; }>
```

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### Interfaces


#### DeviceInfo

| Prop                   | Type                 |
| ---------------------- | -------------------- |
| **`serial`**           | <code>string</code>  |
| **`batteryLevel`**     | <code>number</code>  |
| **`batteryCharging`**  | <code>boolean</code> |
| **`platform`**         | <code>string</code>  |
| **`operatingSystem`**  | <code>string</code>  |
| **`model`**            | <code>string</code>  |
| **`manufacturer`**     | <code>string</code>  |
| **`osVersion`**        | <code>string</code>  |
| **`networkConnected`** | <code>boolean</code> |
| **`ssid`**             | <code>string</code>  |
| **`networkType`**      | <code>string</code>  |

</docgen-api>
