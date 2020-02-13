export class RegisterRequest {
  _pin: string;

  get pin(): string {
    return this._pin;
  }
  set pin(value: string) {
    this._pin = value;
  }
}
