import {
  FETCH_T_STARTED,
  FETCH_T_SUCCESS,
  FETCH_T_FAILURE,
  FETCH_NON_AUTHORIZED,

} from './Actions600'


const initialstate = {
    loading: false,
    error: null,
    temp: 0
};


export default function Reducers600 (state = initialstate, action) {
  switch (action.type) {
    case FETCH_T_STARTED:
      return {
        ...state,
          loading: true,
          error: null,
          temp: 0
      };
    case FETCH_T_SUCCESS:
      return {
        ...state,
          loading: false,
          error: null,
          temp: action.payload.temp
      };
    case FETCH_T_FAILURE:
      return {
        ...state,
          loading: false,
          error: action.payload.error,
          temp: " ERROR: NO DATA Available"
      };
    case FETCH_NON_AUTHORIZED:
      return {
        ...state,
        loading: false,
        error: action.payload.error,
        temp: "ERROR: " + action.payload.error
      };
    default:
      return state;
  }
}


