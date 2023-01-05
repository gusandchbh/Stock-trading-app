<template>
  <div class="app">
    <h4>{{ "Welcome " + this.username + "!"}}</h4>
    <h4>{{ "You have been a user since " +  this.createdTime.substring(0, 10) + "." }}</h4>
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
  async created() {
    await this.loadData()
  },
  methods: {
    async get() {
      await this.loadData();
    },
    async loadData() {
      console.log(useAuthStore().token);
      const response = await Api.get(
        "/users/?username=" + useAuthStore().user
      );
      const { id, username, password, createdTime, role } = response.data;
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
