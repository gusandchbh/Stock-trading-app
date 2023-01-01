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
        </div>
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
      firstName: "",
      lastName: "",
      birthDate: "",
      gender: "",
    };
  },
  methods: {
    onReset() {
      this.username = null;
      this.password = null;
      this.firstName = null;
      this.lastName = null;
      this.birthDate = null;
      this.gender = null;
    },
    onSubmit() {
      if (this.gender !== "Male" && this.gender !== "Female") {
        alert(this.gender)
        this.$q.notify({
          color: "red-5",
          textColor: "white",
          icon: "warning",
          message: "You need to select your gender",
        });
      } else {
        this.$q.notify({
          color: "green-4",
          textColor: "white",
          icon: "cloud_done",
          message: "Submitted",
        });
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
