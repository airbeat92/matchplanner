/**
 * Created by Classdumper, User Peter Heusch
 * Creation Date: 06.11.2018 22:24:25
 */

package league;

import de.hft_stuttgart.unittest.annotations.Skeleton;

@Skeleton
public class Match {

    private int home;
    private int guest;

    private Match(int guest, int arg03) {
        this.guest = guest;
        this.home = arg03;
    }

    public int getHome() {
        return this.home;
    }

    public int getGuest() {
        return this.guest;
    }

    public static Match getMatch(League league, int match) {

        int count = league.getTeams() / 2 - 1;

        if (match > count) {
            throw new IllegalArgumentException("Invalid index");
        }

        if (count == match) {
            if (league.getTail() >= league.getTeams() / 2 - 1 || league.getTail() <= league.getTeams() - 3) {
                return new Match(league.getTail(), league.getHead());
            }
        }

        int teamA = league.getUpper(match);
        int teamB = league.getLower(match);

        int summe = teamA + teamB;
        if (summe % 2 == 0) {
            return new Match(Math.min(teamA, teamB), Math.max(teamA, teamB));
        } else {
            return new Match(Math.max(teamA, teamB), Math.min(teamA, teamB));
        }

    }
}
