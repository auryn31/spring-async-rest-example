import Vue from "vue";
import { Component, Prop } from "vue-property-decorator";
import template from "./cars.vue";

@Component({
  mixins: [template],
  components: {}
})
export default class Cars extends Vue {
  msg: string = "List of Cars";
}
