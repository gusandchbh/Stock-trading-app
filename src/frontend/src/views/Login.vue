<template>
  <div class="login-container q-pa-md" style="max-width: 400px">

    <q-form
        @submit="onSubmit"
        @reset="onReset"
        class="q-gutter-md"
    >
      <q-input
          :input-style="{ height: '50px' }"
          filled
          v-model="name"
          label="Your name *"
          hint="Name and surname"
          lazy-rulesh
          :rules="[ val => val && val.length > 0 || 'Please type something']"
      />

      <q-input
          filled
          :input-style="{ height: '50px' }"
          type="number"
          v-model="age"
          label="Your age *"
          lazy-rules
          :rules="[
          val => val !== null && val !== '' || 'Please type your age',
          val => val > 0 && val < 100 || 'Please type a real age'
        ]"
      />

      <q-toggle v-model="accept" label="I accept the license and terms" />

      <div>
        <q-btn label="Submit" type="submit" color="primary"/>
        <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
      </div>
    </q-form>

  </div>
</template>
<script>
import axios from "axios";
export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      quote: '',
    }
  },
  created() {
    this.getQuote()
  },
  methods: {
    async login() {
      try {
        const response = await axios.get('https://api.kanye.rest/')
        console.log(response)
        // login successful, store user data or set cookie
      } catch (error) {
        console.log(error)
      }
    }, async getQuote() {
      try {
        const response = await axios.get('https://api.kanye.rest/')
        this.quote = response.data.quote
      } catch (error){
        console.log(error)
      }
    }
  }
}

</script>
<style>


.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  align-items: center;
}

.quote {
  width: 600px;
  margin: 50px;
  font-family: "Al Bayan", fantasy;
  font-size: 1.2rem;
}

</style>