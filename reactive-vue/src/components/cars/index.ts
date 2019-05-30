import Vue from "vue";
import {
  Component
} from "vue-property-decorator";
import template from "./cars.vue";
import Car from "../../models/car";
import {
  store
} from "../../store";
import {
  readAsyncCars,
  readSyncCars
} from "../../store/store";
import Carloader from "../../services/carloader";
import CarElement from "../car/Car.vue";

@Component({
  mixins: [template],
  components: {}
})
export default class Cars extends Vue {
  msg: string = "List of Cars";
  loading_text: string = "Load Cars";
  carsAsync: Array < Car > = readAsyncCars(store);
  carsSync: Array < Car > = readSyncCars(store);
  loadcars(): void {
    Carloader.loadCarsSync();
    Carloader.loadCarsAsync();
  }
}
