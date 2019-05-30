<template>
  <v-list-tile v-bind:key="car.id">
    <v-list-tile-content>
      <v-list-tile-title>{{car.model}}</v-list-tile-title>
      <v-list-tile-sub-title>{{car.company}}</v-list-tile-sub-title>
    </v-list-tile-content>
    <v-spacer></v-spacer>
  </v-list-tile>
</template>

<script lang="ts">
import Vue from 'vue'
import Car from '../../models/car';
export default class CarElement extends Vue {
    car: Car = new Car(123, "model", "company");
}
</script>
