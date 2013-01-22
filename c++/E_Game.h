#ifndef _E_GAME_
#define _E_GAME_

#include "E_Node.h"
#include "E_Player.h"
#include "E_Color.h"

// constants
#define DEFAULT_X_SIZE 4
#define DEFAULT_Y_SIZE 4

class E_Game {
    public:
        E_Game(E_Player* player_a, E_Player* player_b, int width=DEFAULT_X_SIZE, int height=DEFAULT_Y_SIZE);
        ~E_Game();
        
        int get_x_size() const;
        int get_y_size() const;
        E_Node* const get_node_at(int x, int y) const;
        E_Node* const first_node() const;
        E_Node* const last_node() const;
        bool over();
        E_Player* to_move();
        void set_move(E_Color new_color);
        void toggle_move();
        void make_move();
        
    private:
        int x_size;
        int y_size;
        E_Node** board;
        
        E_Player* player_a;
        E_Player* player_b;
        
        bool a_to_move;
        E_Color current_move;
};

#endif
