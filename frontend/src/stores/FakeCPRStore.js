import Chance from "chance"
const chance = new Chance()
import { action, computed, observable, toJS } from "mobx";

const delay = ms => new Promise(r => setTimeout(r, ms))

class FakeCPRStore {
  @observable error = null
  @observable isFetching = false
  @observable citizen = null

  @action async fetchCPRregister(ssn) {
    this.isFetching = true
    this.error = null
    try {
      await delay(2500)
      this.citizen = {
        name: chance.name({ nationality: 'en', gender: (ssn.slice(-1) % 2 === 0) ? 'female' : 'male' }),
        address: chance.address(),
        city: chance.city(),
        zip: chance.areacode().replace(/[()]/g, ''),
        phoneNumber: chance.phone({ formatted: false, max: 8 }).slice(0,8)
      }
      this.isFetching = false
    } catch (error) {
      this.error = error
      this.isFetching = false
    }
  }

  @action reset = () => {
    this.citizen = null
  }

}

const fakeCPRStore = new FakeCPRStore()
export default fakeCPRStore
