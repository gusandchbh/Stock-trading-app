<template>
  <div class="app">
    <div class="register-container q-pa-md" style="max-width: 400px">
      <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
        <q-input
          :input-style="{ height: '50px' }"
          filled
          v-model="username"
          label="Your username *"
          lazy-rules
          :rules="[(val) => (val && val.length > 0) || 'Please type something']"
        />
        <q-input
          :input-style="{ height: '50px' }"
          filled
          type="password"
          v-model="password"
          label="Your password *"
          lazy-rules
          :rules="[(val) => (val && val.length > 0) || 'Please type something']"
        />
        <q-input
          :input-style="{ height: '50px' }"
          filled
          v-model="firstName"
          label="Your firstname *"
          lazy-rules
          :rules="[(val) => (val && val.length > 0) || 'Please type something']"
        />
        <q-input
          :input-style="{ height: '50px' }"
          filled
          v-model="lastName"
          label="Your lastname *"
          lazy-rules
          :rules="[(val) => (val && val.length > 0) || 'Please type something']"
        />
        <q-input filled v-model="birthDate" label="Your date of birth *" mask="date" :rules="['date']">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy>
                <q-date v-model="birthDate"></q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
        <div>
          <q-radio v-model="gender" val="Male" label="Male" />
          <q-radio v-model="gender" val="Female" label="Female" />
          <div class="q-px-sm">
            Your selection is: <strong>{{ gender }}</strong>
          </div>
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
      firstName: "",
      lastName: "",
      birthDate: "",
      gender: "",
    };
  },
  methods: {
    createAccount() {
      // Your code to create the account goes here
      // You can access the form data using `this.username`, `this.password`, etc.
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
