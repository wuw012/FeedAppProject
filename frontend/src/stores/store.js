import { defineStore } from 'pinia'

export const useStore = defineStore('store', {
state: () => ({
    username: '',
    email: '',
    password: '',
    isAuthenticated: false,
    isAdmin: false,
    pollID: 0,
    question: '',
    startTime: '',
    endTime: '',
  }),
  actions: {
    increment() {
      this.count++
    },
  },
})

