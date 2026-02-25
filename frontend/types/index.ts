// Enums
export enum Categoria {
  BRONZE = "BRONZE",
  PRATA = "PRATA",
  OURO = "OURO",
  DIAMANTE = "DIAMANTE",
  MESTRE = "MESTRE",
}

export enum StatusJogador {
  ATIVO = "ATIVO",
  BANIDO = "BANIDO",
  INATIVO = "INATIVO",
}

export enum GeneroJogo {
  FPS = "FPS",
  MOBA = "MOBA",
  BATTLE_ROYALE = "BATTLE_ROYALE",
  FIGHTING = "FIGHTING",
  RACING = "RACING",
  SPORTS = "SPORTS",
  RTS = "RTS",
  CARD_GAME = "CARD_GAME",
}

export enum Plataforma {
  PC = "PC",
  PLAYSTATION = "PLAYSTATION",
  XBOX = "XBOX",
  NINTENDO = "NINTENDO",
  MOBILE = "MOBILE",
  MULTIPLATAFORMA = "MULTIPLATAFORMA",
}

export enum StatusTorneio {
  INSCRICOES_ABERTAS = "INSCRICOES_ABERTAS",
  EM_ANDAMENTO = "EM_ANDAMENTO",
  FINALIZADO = "FINALIZADO",
  CANCELADO = "CANCELADO",
}

export enum FormatoTorneio {
  ELIMINACAO_SIMPLES = "ELIMINACAO_SIMPLES",
  ELIMINACAO_DUPLA = "ELIMINACAO_DUPLA",
  PONTOS_CORRIDOS = "PONTOS_CORRIDOS",
  GRUPOS = "GRUPOS",
}

export enum StatusPartida {
  AGENDADA = "AGENDADA",
  EM_ANDAMENTO = "EM_ANDAMENTO",
  FINALIZADA = "FINALIZADA",
  WO = "WO",
}

export enum TipoPenalidade {
  WO = "WO",
  COMPORTAMENTO = "COMPORTAMENTO",
  TRAPACA = "TRAPACA",
  ATRASO = "ATRASO",
}

export enum StatusPenalidade {
  ATIVA = "ATIVA",
  CUMPRIDA = "CUMPRIDA",
  REVOGADA = "REVOGADA",
}

// Models
export interface Jogador {
  nickname: string;
  nomeReal: string;
  email: string;
  dataNascimento: string;
  elo: number;
  status: StatusJogador;
  categoria: Categoria;
  totalPartidas: number;
  vitorias: number;
  derrotas: number;
  kills: number;
  deaths: number;
  assists: number;
  tempoJogoMinutos: number;
  modoFavorito?: string;
  kdRatio: number;
  winRate: number;
}

export interface Jogo {
  nome: string;
  genero: GeneroJogo;
  maxJogadoresPorTime: number;
  plataforma: Plataforma;
}

export interface Time {
  nome: string;
  tag: string;
  capitao: Jogador;
  membros: Jogador[];
  jogoPrincipal: Jogo;
}

export interface Torneio {
  nome: string;
  jogo: Jogo;
  formato: FormatoTorneio;
  dataInicio: string;
  dataFim: string;
  status: StatusTorneio;
  premioTotal: number;
  participantes: (Jogador | Time)[];
  partidas: Partida[];
}

export interface Partida {
  participante1: Jogador | Time;
  participante2: Jogador | Time;
  placar1: number;
  placar2: number;
  dataHora: string;
  status: StatusPartida;
  rodada: number;
  vencedor?: Jogador | Time;
}

export interface Penalidade {
  jogador: Jogador;
  tipo: TipoPenalidade;
  descricao: string;
  data: string;
  status: StatusPenalidade;
}

export interface Premiacao {
  torneio: Torneio;
  participante: Jogador | Time;
  colocacao: number;
  valor: number;
}

// API Response Types
export interface RankingEntry {
  jogador: Jogador;
  posicao: number;
}

export interface WinRateEntry {
  time: Time;
  winRate: number;
  vitorias: number;
  totalPartidas: number;
}

export interface EstatisticasGerais {
  totalJogadores: number;
  totalTimes: number;
  totalTorneios: number;
  torneiosAtivos: number;
  totalPartidas: number;
  partidasFinalizadas: number;
  jogadoresNaFila: number;
  totalPenalidades: number;
  penalidadesAtivas: number;
  eloMedio: number;
}

export interface DistribuicaoCategoria {
  categoria: Categoria;
  quantidade: number;
}
