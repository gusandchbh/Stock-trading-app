<template>
  <div class="app">
    <h4>{{ this.id }}</h4>
    <h4>{{ this.username }}</h4>
    <h4>{{ this.password }}</h4>
    <h4>{{ this.createdTime }}</h4>
    <h4>{{ this.role }}</h4>
    <div class="q-pa-md q-gutter-sm">
      <q-btn
        icon="phone"
        @click="get"
        label="GET"
        stack
        glossy
        color="purple"
      />
    </div>
    <div class="q-pa-md q-gutter-sm">
      <q-btn
        icon="phone"
        @click="logout"
        label="LOGOUT"
        stack
        glossy
        color="purple"
      />
    </div>
  </div>
</template>

<script>
import { useAuthStore } from "@/stores/auth";
import { Api } from "@/Api";
export default {
  // eslint-disable-next-line vue/multi-word-component-names

  name: "UserPage",
  data() {
    return {
      id: "",
      username: "",
      password: "",
      createdTime: "",
      role: "",
    };
  },
  async created() {},
  methods: {
    async get() {
      await this.loadData();
    },
    async loadData() {
      console.log(useAuthStore().token);
      const response = await Api.get("/users/");
      const { id, username, password, createdTime, role } = response.data[0];
      this.id = id;
      this.username = username;
      this.password = password;
      this.createdTime = createdTime;
      this.role = role;
      console.log(this.id);
    },
    logout() {
      useAuthStore().logout();
    },
  },
};
</script>

<style scoped></style>
