// Code Wars 2011 - Power Gaming Calculator
// C++ solution by Lee Jenkins

#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Quest
{
   public:
      string name;
      int   baseValue;
      int   level;
      float time;
      int   getCurrentValue( int charLevel, int completions )
      {
         float percentReduction = ( completions + charLevel - level ) * 10.0;
         if( percentReduction > 100 )
            percentReduction = 100;
         return baseValue - int( percentReduction * float(baseValue) / 100.0 );
      };
};

class QuestDatabase
{
   private:
      // the master list of all the quests
      vector<Quest*> questList;
      // completionList contains the current working solution
      vector<string> completionList;
      // minTime and minCompletionList hold the best solution found so far
      float minTime;
      vector<string> minCompletionList;
      // goal constraints
      float globalMinEfficiency;
      int XPGOAL;

      int getCompletions( const string& questName )
      {
         int completions = 0;
         vector<string>::iterator name;
         for( name=completionList.begin(); name!=completionList.end(); name++ )
         {
            if( *name == questName )
               ++completions;
         }
         return completions;
      };

      Quest* getQuest( const string& questName )
      {
         Quest* quest;
         vector<Quest*>::iterator iq;
         for( iq=questList.begin(); iq!=questList.end(); iq++ )
         {
            if( (*iq)->name == questName )
               quest = *iq;
         }
         return quest;
      };

      int getQuestValueXP( Quest* quest, int characterLevel )
      {
         return quest->getCurrentValue( characterLevel, getCompletions( quest->name ) );
      };

      // we compute an efficiency cutoff as part of an execution time
      // optimization. every quest we can eliminate from consideration will
      // reduce the run-time considerably, but we don't want to eliminate
      // quests that might be part of the solution. we can't know for certain
      // which ones to eliminate so we "guess" by using a heuristic of culling
      // 1/3 of the quests based on efficiency. in practice this causes the
      // program to miss some optimal solutions, but it improves the run-time
      // performance. try just returning zero here to find the truly optimal
      // solution, but be prepared to wait!
      float determineEfficiencyCutoff( int xp )
      {
         int characterLevel = 1 + ( xp / 1000 );
         vector<Quest*>::iterator iq;
         float fMin = 9e9;
         float fMax = 0;
         float xMax = 0;
         for( iq=questList.begin(); iq != questList.end(); iq++ )
         {
            if( (*iq)->level <= characterLevel )
            {
               int qxp = getQuestValueXP( *iq, characterLevel );
               if( xMax < qxp )
                  xMax = qxp;

               float qt = (*iq)->time;
               float efficiency = float(qxp) / qt;
               if( fMin > efficiency )
                  fMin = efficiency;
               if( fMax < efficiency )
                  fMax = efficiency;
            }
         }
         float efficiencyCutoff = ( 2*fMin + fMax ) / 3;
         // ignore efficiency near the end goal
         if( xp + 2*xMax >= XPGOAL )
            efficiencyCutoff = 0;

         return efficiencyCutoff;
      };

   public:
      QuestDatabase() :
         globalMinEfficiency( 9e9 ),
         minTime( 9e9 ),
         XPGOAL( 4000 )
      {
      };

      void Read()
      {
         int numQuests;
         cin >> numQuests;
         for( int i=0; i<numQuests; i++ )
         {
            Quest* q = new Quest();
            questList.push_back( q );
            cin >> q->name >> q->level >> q->baseValue >> q->time;
         }
         vector<Quest*>::iterator iq;
         for( iq=questList.begin(); iq != questList.end(); iq++ )
         {
            float efficiency = float( (*iq)->baseValue ) / (*iq)->time;
            if( efficiency < globalMinEfficiency )
               globalMinEfficiency = efficiency;

            // cout << "name: " << (*quest)->name << endl;
            // cout << "level: " << (*quest)->level << endl;
            // cout << "value: " << (*quest)->baseValue << endl;
            // cout << "time: " << (*quest)->time << endl;
            // cout << "-------------------" << endl;
         }
      };

      // the basic process here is a recursive, brute-force approach. each
      // time this method is called we select one new quest by trying nearly
      // every possible quest, excluding some based on efficiency. for each
      // new quest that we try, this method is recursively called to find the
      // next quest. each time we reach the level goal we check to see if we
      // found a better (faster) solution than before.
      void ComputeSolution( int xp, float minutes )
      {
         // did we reach the level/xp goal?
         if( xp >= XPGOAL )
         {
            // did we find a better solution than last time?
            if( minutes < minTime )
            {
               minTime = minutes;
               minCompletionList.clear();
               vector<string>::iterator name;
               for( name=completionList.begin(); name!=completionList.end(); name++ )
                  minCompletionList.push_back( *name );
            }
            return;
         }

         float minEfficiency = determineEfficiencyCutoff( xp );

         int characterLevel = 1 + ( xp / 1000 );
         vector<Quest*>::iterator iq;
         for( iq=questList.begin(); iq != questList.end(); iq++ )
         {
            int qxp = getQuestValueXP( *iq, characterLevel );
            float qt = (*iq)->time;
            float totalTime  = minutes + qt;
            float efficiency = float(qxp) / qt;
            if( (*iq)->level <= characterLevel && qxp > 0 &&
                 totalTime < minTime && efficiency > minEfficiency )
            { 
               completionList.push_back( (*iq)->name );

               // for( int i=1; i<characterLevel; i++ )
               //    cout << "   ";
               // cout << "<" << (*quest)->name
               //             << " qxp='" << qxp << "'"
               //             << " xp='" << xp+qxp << "'"
               //             << " qt='" << qt << "'"
               //             << " t='" << minutes+qt << "'"
               //             << " f='" << efficiency << "'"
               //             << ">" << endl;

               ComputeSolution( xp + qxp, minutes + qt );

               // for( int i=1; i<characterLevel; i++ )
               //    cout << "   ";
               // cout << "</" << (*quest)->name << ">" << endl;

               completionList.pop_back();
            }
         };
      };
      void PrintSolution()
      {
         completionList.clear();
         int xp = 0;
         float totalTime = 0;
         // cout << "quest,q-level,q-xp,q-time,char-level-before,char-xp-after" << endl;
         vector<string>::iterator name;
         for( name=minCompletionList.begin(); name!=minCompletionList.end(); name++ )
         {
            Quest* quest = getQuest( *name );
            int characterLevel = 1 + xp / 1000;
            int qxp = getQuestValueXP( quest, characterLevel );
            totalTime += quest->time;
            xp += qxp;

            // cout << *name << "," 
            //      << quest->level << ","
            //      << qxp << ","
            //      << quest->time << ","
            //      << characterLevel << ","
            //      << xp
            //      <<endl;

            completionList.push_back( *name );
         }

         cout << totalTime << " " << xp << endl;
         for( name=minCompletionList.begin(); name!=minCompletionList.end(); name++ )
            cout << *name << endl;
      };
};

int main( int argc, char* argv[] )
{
   QuestDatabase qd;
   qd.Read();
   qd.ComputeSolution( 0, 0 );
   qd.PrintSolution();
}

