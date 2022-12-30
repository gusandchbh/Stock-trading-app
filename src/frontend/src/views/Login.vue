<template>
  <div class="login-container">
  <div class="quote">
    <h1> {{this.quote}} <br> - Ye</h1>
  </div>
  <form @submit.prevent="login">
    <input id="username" v-model="username" placeholder="Username" />

    <input id="password" v-model="password" type="password" placeholder="Password" />
    <br />
    <button type="submit">Log In</button>
  </form>
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
  font-family: "Al Bayan";
  font-size: 1.2rem;

}
form {
  display: flex;
  flex-direction: column;
}
input {
  margin: .5rem;
  padding: 1.5rem;
  border: 1px solid #2c3e50;
  border-radius: 6px;
  width: 15rem;
  height: 1.5rem;
}
button {
  margin: .5rem;
  border: 1px solid #2c3e50;
  border-radius: 6px;
  width: 15rem;
  height: 3rem;
  font-size: 1.1rem;
  font-weight: bold;
  text-align: center;
}

button:hover {
  background-color: #2c3e50;
}
</style>