import React, { Component } from "react";
import { inject, observer } from "mobx-react";

@inject("journalStore")
@observer
export default class Journal extends Component {
  constructor(props) {
    super(props);
    // Maps a method over in another metod -> getCitizens is now the same as fetchCitizens
    this.getJournal = (id) => this.props.journalStore.fetchJournal(id);
  }

  // Metode der kører før FØRSTE render
  componentWillMount() {
    const { citizenID } = this.props;
    this.getJournal(citizenID);
  }

  render() {
    const { journalStore } = this.props;
    const { journal } = journalStore;
    return <div>{journal.}</div>;
  }
}
