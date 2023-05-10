function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'hml';
  }
  var config = {
    env: env,
  };
  //configuração de endpoint por ambiente (dev, hml, prd, etc)
  config = karate.read("classpath:domain_authority.yaml")[env];

  if (env == 'dev') {
    // customize
    // e.g. config.foo = 'bar';
  } else if (env == 'e2e') {
    // customize
  }
  //formatação de request e de response
  karate.configure("logPrettyRequest", true);
  karate.configure("logPrettyResponse", true);

  //não perca tempo esperando uma conexão ou se os servidores não responderem em 10 segundos
  karate.configure("connectTimeout",10000);
  karate.configure("readTimeout", 10000);

  //realiza uma nova execução dos testes (chamada HTTP) até quem uma determinada condição seja atendida
  karate.configure('retry', {count: 2, interval: 2000});
  
  return config;
}