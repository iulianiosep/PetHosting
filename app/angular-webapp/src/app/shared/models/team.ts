import { Player } from './player';
import { User } from './user';


export class Team{
  id: number;
  name: string;
  country: string;
  value: number;
  balance: number;
  players: Array<Player>;
  user: User;
}
