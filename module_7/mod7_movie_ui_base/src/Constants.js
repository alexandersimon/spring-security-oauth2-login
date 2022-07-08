const prod = {
  url: {
    KEYCLOAK_BASE_URL: "https://keycloak.emax-it.com/auth",
    API_BASE_URL: 'https://myapp.herokuapp.com',
    OMDB_BASE_URL: 'https://www.omdbapi.com',
    AVATARS_DICEBEAR_URL: 'https://avatars.dicebear.com/api'
  }
}

const dev = {
  url: {
  //ToDo
  }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod