<template>
  <div class="app">
    <h1>{{ test }}</h1>

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
          v-model="firstName"
          label="Your firstname *"
          lazy-rules="ondemand"
          :rules="[(val) => (val && val.length > 0) || 'Please type something']"
        />
        <q-input
          :input-style="{ height: '50px' }"
          filled
          v-model="lastName"
          label="Your lastname *"
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
        <q-input
          filled
          v-model="birthDate"
          label="Your date of birth *"
          mask="date"
          lazy-rules="ondemand"
          :rules="validateDate"
        >
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy>
                <q-date v-model="birthDate">
                  <div class="row items-center justify-end q-gutter-sm">
                    <q-btn
                      label="Reset"
                      color="primary"
                      flat
                      @click="resetDate"
                      v-close-popup
                    />
                    <q-btn label="OK" color="primary" flat v-close-popup />
                  </div>
                </q-date>
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
import { date, Quasar } from "quasar";
const { addToDate } = date;
export default {
  name: "RegisterPage",
  data() {
    return {
      username: "",
      password: "",
      firstName: "",
      lastName: "",
      email: "",
      birthDate: "",
      gender: "",
      today: date.formatDate(Date.now(), "YYYY/MM/DD"),
      test: "",
    };
  },
  computed: {
    validateDate() {
      return [
        (v) => !!v || "Choose a date",
        (v, rules) => rules.date(v) || "Please enter a valid date",
        (v) => v.split("/")[0] < 1900 || "Choose a date after 1900",
      ];
    },
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
    resetDate() {
      this.birthDate = null;
    },
    testmethod() {
      this.test = this.birthDate.split("/")[0];
    },
    onSubmit() {
      console.log(this.birthDate);
      if (this.gender !== "Male" && this.gender !== "Female") {
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
.lol {
  size: 100px;
}
</style>
