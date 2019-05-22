import Vue from "vue";
import { Component, Prop } from "vue-property-decorator";
import template from "./cars.vue";
import Car from "../../models/car";
import { store } from "../../store"

@Component({
  mixins: [template],
  components: {}
})
export default class Cars extends Vue {
  msg: string = "List of Cars";
  loading_text: string = "Load Cars";
  cars: Array<Car> = [new Car("1", "2", "3"), new Car("2", "2", "3"), new Car("3", "2", "3")];
  loadcars (): void {
    console.log("loading cars from 8080");
    console.log(store);
  }
}
