from binarytree import build as build_tree
# class Node:
#     def __init__(self, value, left=None, right=None):
#         self.value = value
#         self.left = left
#         self.right = right

def swap(root):
    # tmp = root.left
    # root.left = right
    # root.right = tmp
    root.left, root.right = root.right, root.left


def inverse_binary_tree(root):
    if root is None:
        return

    swap(root)

    inverse_binary_tree(root.left)
    inverse_binary_tree(root.right)   

root = build_tree([27, 14, 15, 10, 11, 4, 3, 2])
print(root)
#        ____27__
#       /        \
#     _14         15
#    /   \       /  \
#   10    11    4    3
#  /
# 2
inverse_binary_tree(root)
print(root)
#     ___27___
#    /        \
#   15        _14
#  /  \      /   \
# 3    4    11    10
#                   \
#                    2
