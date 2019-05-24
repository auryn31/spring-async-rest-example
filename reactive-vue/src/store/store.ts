import Vue from "vue";
import * as Vuex from "vuex";
import { getStoreAccessors } from "vuex-typescript";
import { State } from "./state";
import Car from "../models/car";

Vue.use(Vuex);

type Context = Vuex.ActionContext<State, State>;

const state: State = {
  splashScreenActive: false,
  loaderActive: false,

  auth: {
    isLoggedIn: false
  },
  cars_async: [],
  cars_sync: []
};

const getters = {
  getSplashScreenState(state: State) {
    return state.splashScreenActive;
  },
  getLoaderState(state: State) {
    return state.loaderActive;
  },
  getLoggedInState(state: State) {
    return state.auth.isLoggedIn;
  },
  getAsyncCars(state: State) {
    return state.cars_async;
  },
  getSyncCars(state: State) {
    return state.cars_sync;
  }
};

const mutations = {
  setSplashScreenVisibility(state: State, splashScreenState: boolean) {
    state.splashScreenActive = splashScreenState;
  },
  setLoaderVisibility(state: State, loaderState: boolean) {
    state.loaderActive = loaderState;
  },
  setLoggedInState(state: State, loggedInState: boolean) {
    state.auth.isLoggedIn = loggedInState;
  },
  addCar(state: State, car: Car) {
    state.cars_async.push(car);
  },
  setCars(state: State, cars: Array<Car>) {
    state.cars_sync.splice(0);
    cars.forEach(a => state.cars_sync.push(a));
  }
};

const actions = {
  loginUser(context: Context, loggedInState: boolean) {
    commitLoggedInState(context, loggedInState);
  },
};

export const createStore = () => new Vuex.Store<State>({
  state: state,
  getters: getters,
  mutations: mutations,
  actions: actions
});

const { read, commit, dispatch } = getStoreAccessors<State, State>("");

/*************************************************/
/* GETTERS */
/*************************************************/
export const readSplashScreenVisibility = read(getters.getSplashScreenState);
export const readLoaderVisibility = read(getters.getLoaderState);
export const readLoggedInState = read(getters.getLoggedInState);
export const readAsyncCars = read(getters.getAsyncCars);
export const readSyncCars = read(getters.getSyncCars);

/*************************************************/
/* MUTATIONS */
/*************************************************/
export const commitSplashScreenVisibility = commit(mutations.setSplashScreenVisibility);
export const commitLoaderVisibility = commit(mutations.setLoaderVisibility);
export const commitLoggedInState = commit(mutations.setLoggedInState);
export const commitAsyncCar = commit(mutations.addCar);
export const commitSyncCars = commit(mutations.setCars);

/*************************************************/
/* ACTIONS */
/*************************************************/
// export const dispatchLoginUser = dispatch(actions.loginUser);
