import Vue from "vue";
import { Component, Prop } from "vue-property-decorator";
import * as Logger from "js-logger";

import Cars from "../../components/cars";
import template from "./home.vue";

@Component({
  mixins: [template],
  components: {
    Cars
  }
})
export default class Home extends Vue {
}
