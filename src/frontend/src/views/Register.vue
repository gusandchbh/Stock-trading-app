<template>
  <div class="app">
    <div class="register-container q-pa-md" style="max-width: 400px">
      <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
        <q-input
          :input-style="{ height: '50px' }"
          filled
          v-model="username"
          label="Your username *"
          lazy-rules="ondemand"
          :rules="[
            (val) =>
              (val && val.length > 0 && val.length < 20) || 'Enter a firstname',
          ]"
        />
        <q-input
          :input-style="{ height: '50px' }"
          filled
          type="password"
          v-model="password"
          label="Your password *"
          lazy-rules="ondemand"
          :rules="[(val) => (val && val.length > 0) || 'Please type something']"
        />
        <q-input
          :input-style="{ height: '50px' }"
          filled
          v-model="email"
          label="Your email *"
          lazy-rules="ondemand"
          :rules="[
            (val, rules) =>
              rules.email(val) || 'Please enter a valid email address',
          ]"
        />
        <div>
          <q-btn label="Submit" type="submit" color="primary" />
          <q-btn
            label="Reset"
            type="reset"
            color="primary"
            flat
            class="q-ml-sm"
          />
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "RegisterPage",
  data() {
    return {
      username: "",
      password: "",
      email: "",
    };
  },
  methods: {
    onReset() {
      this.username = null;
      this.password = null;
      this.email = null;
    },
    onSubmit() {
      this.$q.notify({
        color: "green-4",
        textColor: "white",
        icon: "cloud_done",
        message: "Submitted",
      });
      this.register(this.username, this.password, this.email);
    },
    async register(username, password, email) {
      const response = await fetch("http://localhost:8080/users/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password, email }),
      });
      if (response.status === 201) {
        console.log("CREATED!");
      }
    },
  },
};
</script>
<style>
.register-container {
  margin-top: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  align-items: center;
}
.app {
  margin-top: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  align-items: center;
}
</style>
