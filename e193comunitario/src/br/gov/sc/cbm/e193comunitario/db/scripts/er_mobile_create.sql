CREATE TABLE tp_emergencia (
  id_tp_emergencia BIGINT NOT NULL,
  nm_tp_emergencia VARCHAR(100) NOT NULL,
  is_selected INTEGER UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY(id_tp_emergencia)
);

CREATE TABLE cidades (
  id_cidade BIGINT NOT NULL,
  nm_cidade VARCHAR(100) NOT NULL,
  PRIMARY KEY(id_cidade)
);

CREATE TABLE ocorrencias (
  id_ocorrencia BIGINT NOT NULL,
  id_cidade BIGINT NOT NULL,
  id_tp_emergencia BIGINT NOT NULL,
  ts_ocorrencia TIMESTAMP NOT NULL,
  descricao TEXT NOT NULL,
  logradouro VARCHAR(100) NOT NULL,
  numero BIGINT NULL,
  bairro VARCHAR(100) NOT NULL,
  referencia VARCHAR(100) NULL,
  latitude DOUBLE NULL,
  longitude DOUBLE NULL,
  PRIMARY KEY(id_ocorrencia),
  FOREIGN KEY(id_tp_emergencia)
    REFERENCES tp_emergencia(id_tp_emergencia)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id_cidade)
    REFERENCES cidades(id_cidade)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);



insert into cidades(id_cidade, nm_cidade) values(9939, 'ABDON BATISTA');
insert into cidades(id_cidade, nm_cidade) values(8001, 'ABELARDO LUZ');
insert into cidades(id_cidade, nm_cidade) values(8003, 'AGROLANDIA');
insert into cidades(id_cidade, nm_cidade) values(8005, 'AGRONOMICA');
insert into cidades(id_cidade, nm_cidade) values(8807, 'AGUA DOCE');
insert into cidades(id_cidade, nm_cidade) values(8009, 'AGUAS DE CHAPECO');
insert into cidades(id_cidade, nm_cidade) values(5577, 'AGUAS FRIAS');
insert into cidades(id_cidade, nm_cidade) values(8011, 'AGUAS MORNAS');
insert into cidades(id_cidade, nm_cidade) values(8013, 'ALFREDO WAGNER');
insert into cidades(id_cidade, nm_cidade) values(8869, 'ALTO BELA VISTA');
insert into cidades(id_cidade, nm_cidade) values(8015, 'ANCHIETA');
insert into cidades(id_cidade, nm_cidade) values(8017, 'ANGELINA');
insert into cidades(id_cidade, nm_cidade) values(8019, 'ANITA GARIBALDI');
insert into cidades(id_cidade, nm_cidade) values(8021, 'ANITAPOLIS');
insert into cidades(id_cidade, nm_cidade) values(8023, 'ANTONIO CARLOS');
insert into cidades(id_cidade, nm_cidade) values(9941, 'APIUNA');
insert into cidades(id_cidade, nm_cidade) values(5597, 'ARABUTA');
insert into cidades(id_cidade, nm_cidade) values(8025, 'ARAQUARI');
insert into cidades(id_cidade, nm_cidade) values(8027, 'ARARANGUA');
insert into cidades(id_cidade, nm_cidade) values(8029, 'ARMAZEM');
insert into cidades(id_cidade, nm_cidade) values(8031, 'ARROIO TRINTA');
insert into cidades(id_cidade, nm_cidade) values(5599, 'ARVOREDO');
insert into cidades(id_cidade, nm_cidade) values(8033, 'ASCURRA');
insert into cidades(id_cidade, nm_cidade) values(8035, 'ATALANTA');
insert into cidades(id_cidade, nm_cidade) values(8037, 'AURORA');
insert into cidades(id_cidade, nm_cidade) values(8885, 'BALNEARIO ARROIO DO SILVA');
insert into cidades(id_cidade, nm_cidade) values(8039, 'BALNEARIO CAMBORIU');
insert into cidades(id_cidade, nm_cidade) values(8907, 'BALNEARIO GAIVOTA');
insert into cidades(id_cidade, nm_cidade) values(1192, 'BALNEARIO RINCAO');
insert into cidades(id_cidade, nm_cidade) values(8923, 'BANDEIRANTE');
insert into cidades(id_cidade, nm_cidade) values(8940, 'BARRA BONITA');
insert into cidades(id_cidade, nm_cidade) values(5549, 'BARRA DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8041, 'BARRA VELHA');
insert into cidades(id_cidade, nm_cidade) values(8966, 'BELA VISTA DO TOLDO');
insert into cidades(id_cidade, nm_cidade) values(5745, 'BELMONTE');
insert into cidades(id_cidade, nm_cidade) values(8043, 'BENEDITO NOVO');
insert into cidades(id_cidade, nm_cidade) values(8045, 'BIGUACU');
insert into cidades(id_cidade, nm_cidade) values(8047, 'BLUMENAU');
insert into cidades(id_cidade, nm_cidade) values(8982, 'BOCAINA DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8389, 'BOM JARDIM DA SERRA');
insert into cidades(id_cidade, nm_cidade) values(9008, 'BOM JESUS');
insert into cidades(id_cidade, nm_cidade) values(9024, 'BOM JESUS DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(8049, 'BOM RETIRO');
insert into cidades(id_cidade, nm_cidade) values(5537, 'BOMBINHAS');
insert into cidades(id_cidade, nm_cidade) values(8051, 'BOTUVERA');
insert into cidades(id_cidade, nm_cidade) values(8053, 'BRACO DO NORTE');
insert into cidades(id_cidade, nm_cidade) values(5557, 'BRACO DO TROMBUDO');
insert into cidades(id_cidade, nm_cidade) values(9040, 'BRUNOPOLIS');
insert into cidades(id_cidade, nm_cidade) values(8055, 'BRUSQUE');
insert into cidades(id_cidade, nm_cidade) values(8057, 'CACADOR');
insert into cidades(id_cidade, nm_cidade) values(8059, 'CAIBI');
insert into cidades(id_cidade, nm_cidade) values(5553, 'CALMON');
insert into cidades(id_cidade, nm_cidade) values(8061, 'CAMBORIU');
insert into cidades(id_cidade, nm_cidade) values(8063, 'CAMPO ALEGRE');
insert into cidades(id_cidade, nm_cidade) values(8065, 'CAMPO BELO DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8067, 'CAMPO ERE');
insert into cidades(id_cidade, nm_cidade) values(8069, 'CAMPOS NOVOS');
insert into cidades(id_cidade, nm_cidade) values(8071, 'CANELINHA');
insert into cidades(id_cidade, nm_cidade) values(8073, 'CANOINHAS');
insert into cidades(id_cidade, nm_cidade) values(9067, 'CAPAO ALTO');
insert into cidades(id_cidade, nm_cidade) values(8075, 'CAPINZAL');
insert into cidades(id_cidade, nm_cidade) values(5545, 'CAPIVARI DE BAIXO');
insert into cidades(id_cidade, nm_cidade) values(8077, 'CATANDUVAS');
insert into cidades(id_cidade, nm_cidade) values(8079, 'CAXAMBU DO SUL');
insert into cidades(id_cidade, nm_cidade) values(9943, 'CELSO RAMOS');
insert into cidades(id_cidade, nm_cidade) values(5567, 'CERRO NEGRO');
insert into cidades(id_cidade, nm_cidade) values(9083, 'CHAPADAO DO LAGEADO');
insert into cidades(id_cidade, nm_cidade) values(8081, 'CHAPECO');
insert into cidades(id_cidade, nm_cidade) values(5543, 'COCAL DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8083, 'CONCORDIA');
insert into cidades(id_cidade, nm_cidade) values(5579, 'CORDILHEIRA ALTA');
insert into cidades(id_cidade, nm_cidade) values(8085, 'CORONEL FREITAS');
insert into cidades(id_cidade, nm_cidade) values(5735, 'CORONEL MARTINS');
insert into cidades(id_cidade, nm_cidade) values(8395, 'CORREIA PINTO');
insert into cidades(id_cidade, nm_cidade) values(8087, 'CORUPA');
insert into cidades(id_cidade, nm_cidade) values(8089, 'CRICIUMA');
insert into cidades(id_cidade, nm_cidade) values(8091, 'CUNHA PORA');
insert into cidades(id_cidade, nm_cidade) values(9105, 'CUNHATAI');
insert into cidades(id_cidade, nm_cidade) values(8093, 'CURITIBANOS');
insert into cidades(id_cidade, nm_cidade) values(8095, 'DESCANSO');
insert into cidades(id_cidade, nm_cidade) values(8097, 'DIONISIO CERQUEIRA');
insert into cidades(id_cidade, nm_cidade) values(8099, 'DONA EMMA');
insert into cidades(id_cidade, nm_cidade) values(9945, 'DOUTOR PEDRINHO');
insert into cidades(id_cidade, nm_cidade) values(9121, 'ENTRE RIOS');
insert into cidades(id_cidade, nm_cidade) values(9148, 'ERMO');
insert into cidades(id_cidade, nm_cidade) values(8101, 'ERVAL VELHO');
insert into cidades(id_cidade, nm_cidade) values(8103, 'FAXINAL DOS GUEDES');
insert into cidades(id_cidade, nm_cidade) values(9164, 'FLOR DO SERTAO');
insert into cidades(id_cidade, nm_cidade) values(8105, 'FLORIANOPOLIS');
insert into cidades(id_cidade, nm_cidade) values(5581, 'FORMOSA DO SUL');
insert into cidades(id_cidade, nm_cidade) values(9733, 'FORQUILHINHA');
insert into cidades(id_cidade, nm_cidade) values(8107, 'FRAIBURGO');
insert into cidades(id_cidade, nm_cidade) values(9180, 'FREI ROGERIO');
insert into cidades(id_cidade, nm_cidade) values(8109, 'GALVAO');
insert into cidades(id_cidade, nm_cidade) values(8113, 'GAROPABA');
insert into cidades(id_cidade, nm_cidade) values(8115, 'GARUVA');
insert into cidades(id_cidade, nm_cidade) values(8117, 'GASPAR');
insert into cidades(id_cidade, nm_cidade) values(8111, 'GOVERNADOR CELSO RAMOS');
insert into cidades(id_cidade, nm_cidade) values(8119, 'GRAO PARA');
insert into cidades(id_cidade, nm_cidade) values(8121, 'GRAVATAL');
insert into cidades(id_cidade, nm_cidade) values(8123, 'GUABIRUBA');
insert into cidades(id_cidade, nm_cidade) values(8125, 'GUARACIABA');
insert into cidades(id_cidade, nm_cidade) values(8127, 'GUARAMIRIM');
insert into cidades(id_cidade, nm_cidade) values(8129, 'GUARUJA DO SUL');
insert into cidades(id_cidade, nm_cidade) values(5583, 'GUATAMBU');
insert into cidades(id_cidade, nm_cidade) values(8131, 'HERVAL DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(9202, 'IBIAM');
insert into cidades(id_cidade, nm_cidade) values(8133, 'IBICARE');
insert into cidades(id_cidade, nm_cidade) values(8135, 'IBIRAMA');
insert into cidades(id_cidade, nm_cidade) values(8137, 'ICARA');
insert into cidades(id_cidade, nm_cidade) values(8139, 'ILHOTA');
insert into cidades(id_cidade, nm_cidade) values(8141, 'IMARUI');
insert into cidades(id_cidade, nm_cidade) values(8143, 'IMBITUBA');
insert into cidades(id_cidade, nm_cidade) values(8145, 'IMBUIA');
insert into cidades(id_cidade, nm_cidade) values(8147, 'INDAIAL');
insert into cidades(id_cidade, nm_cidade) values(9229, 'IOMERE');
insert into cidades(id_cidade, nm_cidade) values(8149, 'IPIRA');
insert into cidades(id_cidade, nm_cidade) values(9951, 'IPORA DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(5737, 'IPUACU');
insert into cidades(id_cidade, nm_cidade) values(8151, 'IPUMIRIM');
insert into cidades(id_cidade, nm_cidade) values(9953, 'IRACEMINHA');
insert into cidades(id_cidade, nm_cidade) values(8153, 'IRANI');
insert into cidades(id_cidade, nm_cidade) values(5585, 'IRATI');
insert into cidades(id_cidade, nm_cidade) values(8155, 'IRINEOPOLIS');
insert into cidades(id_cidade, nm_cidade) values(8157, 'ITA');
insert into cidades(id_cidade, nm_cidade) values(8159, 'ITAIOPOLIS');
insert into cidades(id_cidade, nm_cidade) values(8161, 'ITAJAI');
insert into cidades(id_cidade, nm_cidade) values(8163, 'ITAPEMA');
insert into cidades(id_cidade, nm_cidade) values(8165, 'ITAPIRANGA');
insert into cidades(id_cidade, nm_cidade) values(9985, 'ITAPOA');
insert into cidades(id_cidade, nm_cidade) values(8167, 'ITUPORANGA');
insert into cidades(id_cidade, nm_cidade) values(8169, 'JABORA');
insert into cidades(id_cidade, nm_cidade) values(8171, 'JACINTO MACHADO');
insert into cidades(id_cidade, nm_cidade) values(8173, 'JAGUARUNA');
insert into cidades(id_cidade, nm_cidade) values(8175, 'JARAGUA DO SUL');
insert into cidades(id_cidade, nm_cidade) values(5587, 'JARDINOPOLIS');
insert into cidades(id_cidade, nm_cidade) values(8177, 'JOACABA');
insert into cidades(id_cidade, nm_cidade) values(8179, 'JOINVILLE');
insert into cidades(id_cidade, nm_cidade) values(9957, 'JOSE BOITEUX');
insert into cidades(id_cidade, nm_cidade) values(9245, 'JUPIA');
insert into cidades(id_cidade, nm_cidade) values(8181, 'LACERDOPOLIS');
insert into cidades(id_cidade, nm_cidade) values(5739, 'LAGEADO GRANDE');
insert into cidades(id_cidade, nm_cidade) values(8183, 'LAGES');
insert into cidades(id_cidade, nm_cidade) values(8185, 'LAGUNA');
insert into cidades(id_cidade, nm_cidade) values(8187, 'LAURENTINO');
insert into cidades(id_cidade, nm_cidade) values(8189, 'LAURO MULLER');
insert into cidades(id_cidade, nm_cidade) values(8191, 'LEBON REGIS');
insert into cidades(id_cidade, nm_cidade) values(8193, 'LEOBERTO LEAL');
insert into cidades(id_cidade, nm_cidade) values(9961, 'LINDOIA DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8195, 'LONTRAS');
insert into cidades(id_cidade, nm_cidade) values(8197, 'LUIZ ALVES');
insert into cidades(id_cidade, nm_cidade) values(9261, 'LUZERNA');
insert into cidades(id_cidade, nm_cidade) values(5575, 'MACIEIRA');
insert into cidades(id_cidade, nm_cidade) values(8199, 'MAFRA');
insert into cidades(id_cidade, nm_cidade) values(8201, 'MAJOR GERCINO');
insert into cidades(id_cidade, nm_cidade) values(8203, 'MAJOR VIEIRA');
insert into cidades(id_cidade, nm_cidade) values(8391, 'MARACAJA');
insert into cidades(id_cidade, nm_cidade) values(8205, 'MARAVILHA');
insert into cidades(id_cidade, nm_cidade) values(9963, 'MAREMA');
insert into cidades(id_cidade, nm_cidade) values(8207, 'MASSARANDUBA');
insert into cidades(id_cidade, nm_cidade) values(8209, 'MATOS COSTA');
insert into cidades(id_cidade, nm_cidade) values(8211, 'MELEIRO');
insert into cidades(id_cidade, nm_cidade) values(5559, 'MIRIM DOCE');
insert into cidades(id_cidade, nm_cidade) values(8213, 'MODELO');
insert into cidades(id_cidade, nm_cidade) values(8215, 'MONDAI');
insert into cidades(id_cidade, nm_cidade) values(8217, 'MONTE CASTELO');
insert into cidades(id_cidade, nm_cidade) values(5561, 'MONTECARLO');
insert into cidades(id_cidade, nm_cidade) values(8219, 'MORRO DA FUMACA');
insert into cidades(id_cidade, nm_cidade) values(5539, 'MORRO GRANDE');
insert into cidades(id_cidade, nm_cidade) values(8221, 'NAVEGANTES');
insert into cidades(id_cidade, nm_cidade) values(8223, 'NOVA ERECHIM');
insert into cidades(id_cidade, nm_cidade) values(5589, 'NOVA ITABERABA');
insert into cidades(id_cidade, nm_cidade) values(8225, 'NOVA TRENTO');
insert into cidades(id_cidade, nm_cidade) values(8227, 'NOVA VENEZA');
insert into cidades(id_cidade, nm_cidade) values(5591, 'NOVO HORIZONTE');
insert into cidades(id_cidade, nm_cidade) values(8229, 'ORLEANS');
insert into cidades(id_cidade, nm_cidade) values(8397, 'OTACILIO COSTA');
insert into cidades(id_cidade, nm_cidade) values(8231, 'OURO');
insert into cidades(id_cidade, nm_cidade) values(5741, 'OURO VERDE');
insert into cidades(id_cidade, nm_cidade) values(9288, 'PAIAL');
insert into cidades(id_cidade, nm_cidade) values(9300, 'PAINEL');
insert into cidades(id_cidade, nm_cidade) values(8233, 'PALHOCA');
insert into cidades(id_cidade, nm_cidade) values(8235, 'PALMA SOLA');
insert into cidades(id_cidade, nm_cidade) values(9326, 'PALMEIRA');
insert into cidades(id_cidade, nm_cidade) values(8237, 'PALMITOS');
insert into cidades(id_cidade, nm_cidade) values(8239, 'PAPANDUVA');
insert into cidades(id_cidade, nm_cidade) values(5747, 'PARAISO');
insert into cidades(id_cidade, nm_cidade) values(5541, 'PASSO DE TORRES');
insert into cidades(id_cidade, nm_cidade) values(5743, 'PASSOS MAIA');
insert into cidades(id_cidade, nm_cidade) values(8241, 'PAULO LOPES');
insert into cidades(id_cidade, nm_cidade) values(8243, 'PEDRAS GRANDES');
insert into cidades(id_cidade, nm_cidade) values(8245, 'PENHA');
insert into cidades(id_cidade, nm_cidade) values(8247, 'PERITIBA');
insert into cidades(id_cidade, nm_cidade) values(1194, 'PESCARIA BRAVA');
insert into cidades(id_cidade, nm_cidade) values(8249, 'PETROLANDIA');
insert into cidades(id_cidade, nm_cidade) values(8251, 'PICARRAS');
insert into cidades(id_cidade, nm_cidade) values(8253, 'PINHALZINHO');
insert into cidades(id_cidade, nm_cidade) values(8255, 'PINHEIRO PRETO');
insert into cidades(id_cidade, nm_cidade) values(8257, 'PIRATUBA');
insert into cidades(id_cidade, nm_cidade) values(5593, 'PLANALTO ALEGRE');
insert into cidades(id_cidade, nm_cidade) values(8259, 'POMERODE');
insert into cidades(id_cidade, nm_cidade) values(8261, 'PONTE ALTA');
insert into cidades(id_cidade, nm_cidade) values(5569, 'PONTE ALTA DO NORTE');
insert into cidades(id_cidade, nm_cidade) values(8263, 'PONTE SERRADA');
insert into cidades(id_cidade, nm_cidade) values(8265, 'PORTO BELO');
insert into cidades(id_cidade, nm_cidade) values(8267, 'PORTO UNIAO');
insert into cidades(id_cidade, nm_cidade) values(8269, 'POUSO REDONDO');
insert into cidades(id_cidade, nm_cidade) values(8271, 'PRAIA GRANDE');
insert into cidades(id_cidade, nm_cidade) values(8273, 'PRESIDENTE CASTELO BRANCO');
insert into cidades(id_cidade, nm_cidade) values(8275, 'PRESIDENTE GETULIO');
insert into cidades(id_cidade, nm_cidade) values(8277, 'PRESIDENTE NEREU');
insert into cidades(id_cidade, nm_cidade) values(9342, 'PRINCESA');
insert into cidades(id_cidade, nm_cidade) values(8279, 'QUILOMBO');
insert into cidades(id_cidade, nm_cidade) values(8281, 'RANCHO QUEIMADO');
insert into cidades(id_cidade, nm_cidade) values(8283, 'RIO DAS ANTAS');
insert into cidades(id_cidade, nm_cidade) values(8285, 'RIO DO CAMPO');
insert into cidades(id_cidade, nm_cidade) values(8287, 'RIO DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(8291, 'RIO DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8289, 'RIO DOS CEDROS');
insert into cidades(id_cidade, nm_cidade) values(8293, 'RIO FORTUNA');
insert into cidades(id_cidade, nm_cidade) values(8295, 'RIO NEGRINHO');
insert into cidades(id_cidade, nm_cidade) values(5571, 'RIO RUFINO');
insert into cidades(id_cidade, nm_cidade) values(5749, 'RIQUEZA');
insert into cidades(id_cidade, nm_cidade) values(8297, 'RODEIO');
insert into cidades(id_cidade, nm_cidade) values(8299, 'ROMELANDIA');
insert into cidades(id_cidade, nm_cidade) values(8301, 'SALETE');
insert into cidades(id_cidade, nm_cidade) values(9369, 'SALTINHO');
insert into cidades(id_cidade, nm_cidade) values(8303, 'SALTO VELOSO');
insert into cidades(id_cidade, nm_cidade) values(5547, 'SANGAO');
insert into cidades(id_cidade, nm_cidade) values(8305, 'SANTA CECILIA');
insert into cidades(id_cidade, nm_cidade) values(5751, 'SANTA HELENA');
insert into cidades(id_cidade, nm_cidade) values(8307, 'SANTA ROSA DE LIMA');
insert into cidades(id_cidade, nm_cidade) values(9967, 'SANTA ROSA DO SUL');
insert into cidades(id_cidade, nm_cidade) values(5555, 'SANTA TEREZINHA');
insert into cidades(id_cidade, nm_cidade) values(9385, 'SANTA TEREZINHA DO PROGRESSO');
insert into cidades(id_cidade, nm_cidade) values(9407, 'SANTIAGO DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8309, 'SANTO AMARO DA IMPERATRIZ');
insert into cidades(id_cidade, nm_cidade) values(8311, 'SAO BENTO DO SUL');
insert into cidades(id_cidade, nm_cidade) values(9423, 'SAO BERNARDINO');
insert into cidades(id_cidade, nm_cidade) values(8313, 'SAO BONIFACIO');
insert into cidades(id_cidade, nm_cidade) values(8315, 'SAO CARLOS');
insert into cidades(id_cidade, nm_cidade) values(5573, 'SAO CRISTOVAO DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8317, 'SAO DOMINGOS');
insert into cidades(id_cidade, nm_cidade) values(8319, 'SAO FRANCISCO DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8321, 'SAO JOAO BATISTA');
insert into cidades(id_cidade, nm_cidade) values(5551, 'SAO JOAO DO ITAPERIU');
insert into cidades(id_cidade, nm_cidade) values(5753, 'SAO JOAO DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(8323, 'SAO JOAO DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8325, 'SAO JOAQUIM');
insert into cidades(id_cidade, nm_cidade) values(8327, 'SAO JOSE');
insert into cidades(id_cidade, nm_cidade) values(8329, 'SAO JOSE DO CEDRO');
insert into cidades(id_cidade, nm_cidade) values(8331, 'SAO JOSE DO CERRITO');
insert into cidades(id_cidade, nm_cidade) values(8333, 'SAO LOURENCO DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(8335, 'SAO LUDGERO');
insert into cidades(id_cidade, nm_cidade) values(8337, 'SAO MARTINHO');
insert into cidades(id_cidade, nm_cidade) values(5755, 'SAO MIGUEL DA BOA VISTA');
insert into cidades(id_cidade, nm_cidade) values(8339, 'SAO MIGUEL DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(9440, 'SAO PEDRO DE ALCANTARA');
insert into cidades(id_cidade, nm_cidade) values(8341, 'SAUDADES');
insert into cidades(id_cidade, nm_cidade) values(8343, 'SCHROEDER');
insert into cidades(id_cidade, nm_cidade) values(8345, 'SEARA');
insert into cidades(id_cidade, nm_cidade) values(9989, 'SERRA ALTA');
insert into cidades(id_cidade, nm_cidade) values(8347, 'SIDEROPOLIS');
insert into cidades(id_cidade, nm_cidade) values(8349, 'SOMBRIO');
insert into cidades(id_cidade, nm_cidade) values(5595, 'SUL BRASIL');
insert into cidades(id_cidade, nm_cidade) values(8351, 'TAIO');
insert into cidades(id_cidade, nm_cidade) values(8353, 'TANGARA');
insert into cidades(id_cidade, nm_cidade) values(9466, 'TIGRINHOS');
insert into cidades(id_cidade, nm_cidade) values(8355, 'TIJUCAS');
insert into cidades(id_cidade, nm_cidade) values(8393, 'TIMBE DO SUL');
insert into cidades(id_cidade, nm_cidade) values(8357, 'TIMBO');
insert into cidades(id_cidade, nm_cidade) values(9971, 'TIMBO GRANDE');
insert into cidades(id_cidade, nm_cidade) values(8359, 'TRES BARRAS');
insert into cidades(id_cidade, nm_cidade) values(9482, 'TREVISO');
insert into cidades(id_cidade, nm_cidade) values(8361, 'TREZE DE MAIO');
insert into cidades(id_cidade, nm_cidade) values(8363, 'TREZE TILIAS');
insert into cidades(id_cidade, nm_cidade) values(8365, 'TROMBUDO CENTRAL');
insert into cidades(id_cidade, nm_cidade) values(8367, 'TUBARAO');
insert into cidades(id_cidade, nm_cidade) values(9991, 'TUNAPOLIS');
insert into cidades(id_cidade, nm_cidade) values(8369, 'TURVO');
insert into cidades(id_cidade, nm_cidade) values(9973, 'UNIAO DO OESTE');
insert into cidades(id_cidade, nm_cidade) values(8371, 'URUBICI');
insert into cidades(id_cidade, nm_cidade) values(9975, 'URUPEMA');
insert into cidades(id_cidade, nm_cidade) values(8373, 'URUSSANGA');
insert into cidades(id_cidade, nm_cidade) values(8375, 'VARGEAO');
insert into cidades(id_cidade, nm_cidade) values(5563, 'VARGEM');
insert into cidades(id_cidade, nm_cidade) values(5565, 'VARGEM BONITA');
insert into cidades(id_cidade, nm_cidade) values(8377, 'VIDAL RAMOS');
insert into cidades(id_cidade, nm_cidade) values(8379, 'VIDEIRA');
insert into cidades(id_cidade, nm_cidade) values(9977, 'VITOR MEIRELES');
insert into cidades(id_cidade, nm_cidade) values(8381, 'WITMARSUM');
insert into cidades(id_cidade, nm_cidade) values(8383, 'XANXERE');
insert into cidades(id_cidade, nm_cidade) values(8385, 'XAVANTINA');
insert into cidades(id_cidade, nm_cidade) values(8387, 'XAXIM');
insert into cidades(id_cidade, nm_cidade) values(9504, 'ZORTEA');

insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(8, 'ACIDENTE DE TRÂNSITO');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(5, 'ATENDIMENTO PRÉ-HOSPITALAR');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(2, 'AUXÍLIOS / APOIOS');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(10, 'AVERIGUAÇÃO / CORTE DE ÁRVORE');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(11, 'AVERIGUAÇÃO / MANEJO DE INSETO');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(9, 'AÇÕES PREVENTIVAS');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(7, 'DIVERSOS');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(1, 'INCÊNDIO');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(6, 'OCORRÊNCIA NÃO ATENDIDA');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(3, 'PRODUTOS PERIGOSOS');
insert into tp_emergencia(id_tp_emergencia, nm_tp_emergencia) values(4, 'SALVAMENTO / BUSCA / RESGATE');
