import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faPen } from "@fortawesome/free-solid-svg-icons/faPen";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons/faChevronDown";
import { faChevronUp } from "@fortawesome/free-solid-svg-icons/faChevronUp";
import { faFloppyDisk } from "@fortawesome/free-solid-svg-icons/faFloppyDisk";
import { faXmark } from "@fortawesome/free-solid-svg-icons/faXmark";

const icons = [
  faPen,
  faChevronUp,
  faChevronDown,
  faFloppyDisk,
  faXmark
]

const startIconLibrary = () => {
  icons.forEach(icon => library.add(icon))

  return {
    componentName: 'fa-icon',
    FontAwesomeIcon
  }
}

export default startIconLibrary
