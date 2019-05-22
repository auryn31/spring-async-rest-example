<template>
  <div class="cars">
    <h1>{{ msg }}</h1>
    <div id="example">
      <button v-on:click="loadcars">{{loading_text}}</button>
    </div>
    <div class="carlistcontainer">
      <ul id="carlist-1">
        <li v-for="car in cars" v-bind:key="car.name">
          {{ car }}
        </li>
      </ul>
      <ul id="carlist-2">
        <li v-for="car in cars" v-bind:key="car.name">
          {{ car }}
        </li>
      </ul>
    </div>
  </div>
</template>
<style lang="scss" src="./cars.scss" scoped></style>
