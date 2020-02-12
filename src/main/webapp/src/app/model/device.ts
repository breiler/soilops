import {Observation} from "./observation";

export class Device {
  uuid: string;
  name: string;
  created: string;
  latestObservation: Observation;
}
