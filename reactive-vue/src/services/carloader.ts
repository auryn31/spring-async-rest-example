import axios from "axios";
import oboe from "oboe";
import { store } from "../store";
import {
  commitSyncCars,
  commitAsyncCar
} from "../store/store";

export default class Carloader {

  public static loadCarsSync() {
    axios.get("http://localhost:8080/cars?sync", {
        headers: {
          "Accept": "application/json"
        }
      })
      .then(response => {
        commitSyncCars(store, response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }

  public static loadCarsAsync() {
    oboe({
        url: "http://localhost:8080/cars?async",
        headers: {
          "Accept": "application/stream+json"
        }
      })
      .node("{id model company}", a => {
        commitAsyncCar(store, a);
      });
  }
}
