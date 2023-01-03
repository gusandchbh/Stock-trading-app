<template>
  <div class="app">
    <h1>{{ testData }}</h1>
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
import { Api } from '@/Api'
export default {
  // eslint-disable-next-line vue/multi-word-component-names

  name: "UserPage",
  data() {
    return {
      testData: "",
    };
  },
  async created() {},
  methods: {
    async get() {
      await this.loadData();
    },
    async loadData() {
      console.log(useAuthStore().token);
      const response = await Api.get("/users/", {
        headers: {
          Authorization: `Bearer ${useAuthStore().token}`,
        },
      });
      this.testData = response.data
    },
  },
};
</script>

<style scoped></style>
