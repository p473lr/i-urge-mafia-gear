// ============================================================================
//
// Level Up program - Code Wars 2009
// written by Lee Jenkins
//
// ============================================================================

#include <vector>
#include <string>
#include <iostream>

using namespace std;

class SkillNode
{
    public:
        string name;
        vector<string> dependencies;
        SkillNode( string newName ) : name( newName )
        {
            /* nothing to see here */
        };
};

class SkillDepth
{
    public:
        string name;
        int maxDepth;
        SkillDepth( string newName, int newDepth ) : 
            name( newName ), maxDepth( newDepth )
        {
            /* nothing to see here */
        };
};

//
// a vector is a dynamic array
//
typedef vector<SkillDepth*> SkillDepthList;
typedef vector<SkillNode*>  SkillNodeList;

char LastChar( string s )
{
    return s[ s.length()-1 ];
}

void TrimLastChar( string& s )
{
    s = s.substr( 0, s.length()-1 );
}

//
// GetSkillNode searches the nodeList for a name. easy.
// for this contest problem we assume the name will always be found.
//
SkillNode* GetSkillNode( SkillNodeList& nodeList, string name )
{
    for( int i=0; i<nodeList.size(); i++ )
        if( nodeList[i]->name == name )
            return nodeList[i];
}

//
// GetDepthNode searches the depthList for a name. easy.
// return NULL if the name cannot be found
//
SkillDepth* GetDepthNode( SkillDepthList& depthList, string name )
{
    for( int i=0; i<depthList.size(); i++ )
        if( depthList[i]->name == name )
            return depthList[i];

    return NULL;
}

//
// FindDependencies is the real work-horse of this program
//
// the target skills are added to depthList in function main. those skills
// are given a depth of zero.
//
// this function loops through the depthList, examining only those skills
// at the current maxDepth (initially zero). for each skill at that depth,
// all the dependent skills are added to the depthList at a depth of
// maxDepth+1. if the dependent skill is already in the depthList, the depth
// of that skill is revised to maxDepth+1. at the end of each loop maxDepth
// is incremented by one. the process continues until no more dependencies
// can be found.
//
int FindDependencies( SkillDepthList& depthList, SkillNodeList& nodeList )
{
    int maxDepth;
    int skillCount;
    SkillNode* skillNode;
    SkillDepth* depthNode;

    // cout << endl;

    maxDepth = 0;
    do
    {
        skillCount = 0;

        // cout << "examining skills at depth " << maxDepth << endl;

        for( int i=0; i<depthList.size(); i++ )
        {
            if( depthList[i]->maxDepth != maxDepth )
                continue;

            ++skillCount; // skills counted for this depth only

            skillNode = GetSkillNode( nodeList, depthList[i]->name );

            // cout << "skill " << skillNode->name << " depends on:" << endl;

            //
            // check to see if the dependent skill is already in the list
            //
            for( int j=0; j<skillNode->dependencies.size(); j++ )
            {
                // cout << "    " << skillNode->dependencies[j];
                depthNode = GetDepthNode( depthList, skillNode->dependencies[j] );
                if( depthNode != NULL )
                {
                    // skill found, set its depth to the next depth
                    depthNode->maxDepth = maxDepth + 1;
                    // cout << " at a revised depth of " << depthNode->maxDepth << endl;
                }
                else
                // if not found, then add the skill at the next depth
                {
                    int newDepth = maxDepth+1;
                    // cout << " at a depth of " << newDepth << endl;
                    depthList.push_back( new SkillDepth( skillNode->dependencies[j], maxDepth+1 ) );
                }
            }
        }
        ++maxDepth;
    
    } while( skillCount > 0 );

    return maxDepth;
}

//
// print skills in reverse depth order (high to low)
//
void PrintSkillsByDepth( SkillDepthList& depthList, int depth )
{
    while( depth >= 0 )
    {
        for( int i=0; i<depthList.size(); i++ )
            if( depthList[i]->maxDepth == depth )
                cout << " " << depthList[i]->name;
        --depth;
    }
    cout << endl;
}



int main( int argc, char* argv[] )
{
    SkillNode* skillNode;
    SkillNodeList nodeList;
    SkillDepthList depthList;
    string token;
    int depth;
    bool newList;

    newList = true;

    cin >> token; // [Skills]

    skillNode = NULL;

    for( cin >> token; token != "[Goals]"; cin >> token )
    {
         // inside this loop, token is a skill name
        if( newList )
        {
            if( LastChar( token ) == ':' )
            {
                TrimLastChar( token ); // remove the colon from the skill name
                // token ended in a colon, so we're not at the end of the list
                newList = false;
            }
            // create a new skill node for this skill
            skillNode = new SkillNode( token );
            nodeList.push_back( skillNode ); // add skillNode to nodeList
            // we can still add dependencies to skillNode even after it has
            // been added to the nodeList
        }
        else
        {
            if( LastChar( token ) == ',' )
                TrimLastChar( token ); // remove the comma from the skill name
            else  // token didn't end in a comma, so we're at the end of the list
                newList = true;
            // add this skill to the dependency list
            skillNode->dependencies.push_back( token ); // add token to dependencies
        }
    }
    // evaluate the goals
    // read one word (token) at a time from the input
    for( cin >> token; token != "[End]"; cin >> token )
    {
        if( newList )
        {
            //
            if( depthList.size() > 0 )
            {
                depth = FindDependencies( depthList, nodeList );
                PrintSkillsByDepth( depthList, depth );
                // clear out the depth list to prepare for the next input line
                while( depthList.size() > 0 )
                {
                    delete depthList[ depthList.size()-1 ];
                    depthList.pop_back(); // remove last item in depthList
                }
            }
            // if( LastChar( token ) == ':' ) // always true here
            {
                // token ended in a colon, so we're not at the end of the list
                newList = false;
                cout << token;
            }
        }
        else
        {
            if( LastChar( token ) == ',' )
                TrimLastChar( token ); // remove the comma from the skill name
            else  // token didn't end in a comma, so we're at the end of the list
                newList = true;
            // add new SkillDepth object to depthList
            depthList.push_back( new SkillDepth( token, 0 ) );
        }
    }
    if( depthList.size() > 0 )
    {
        depth = FindDependencies( depthList, nodeList );
        PrintSkillsByDepth( depthList, depth );
    }
}
