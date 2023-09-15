import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faPen } from "@fortawesome/free-solid-svg-icons/faPen";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons/faChevronDown";
import { faChevronUp } from "@fortawesome/free-solid-svg-icons/faChevronUp";

const icons = [
  faPen,
  faChevronUp,
  faChevronDown
]

const startIconLibrary = () => {
  icons.forEach(icon => library.add(icon))

  return {
    componentName: 'fa-icon',
    FontAwesomeIcon
  }
}

export default startIconLibrary
